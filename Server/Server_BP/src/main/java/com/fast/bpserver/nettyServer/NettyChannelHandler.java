package com.fast.bpserver.nettyServer;

import com.fast.bpserver.entity.postEntity.WebSocketCommand;
import com.fast.bpserver.entity.vo.ComputerData;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.JsonToObjectUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Nelson on 2019/12/20.
 */
@Component
@ChannelHandler.Sharable
public class NettyChannelHandler extends SimpleChannelInboundHandler<Object> {
    @Autowired
    private CacheUtil cacheUtil;
    private static final Logger logger = LoggerFactory.getLogger(NettyChannelHandler.class);
    private static final String URI = "websocket";

    private WebSocketServerHandshaker handshaker;

    /**
     * 接收到Client 传来的数据后 触发的事件
     * 实现channelRead 后此函数不被触发 但是必须实现
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        logger.info(msg.toString());
    }

    /**
     * 接收到数据后 触发的事件
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            //http 协议处理 webSockt 第一次建立连接时 会使用HTTP 协议
//            logger.info("Http Request");
            doHandlerHttpRequest(ctx, (HttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            //websocket 协议处理 webSocket 通讯时 使用该协议
            doHandlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        } else {
            // 其他协议 默认为TCP/IP 协议  程序需要校验数据结构 如果不符合则不处理
            logger.info("receive TCP/IP data");
            if (doTCPData(ctx, msg)) MessageWebClient();
        }
    }

    private boolean doTCPData(ChannelHandlerContext ctx, Object msg) {
        if (ctx == null || msg == null) {
            return false;
        }
        try {
            String content = new String((byte[]) msg, "utf-8");
            if (content.equals("1")) {
                return false;
            }
            ComputerData cd = JsonToObjectUtil.jsonToPojo(content, ComputerData.class);
            if (cd == null) return false;;
            String ip = getIPString(ctx);
            if (!GlobalUserUtil.tcpChannelMap.containsKey(ip))
                GlobalUserUtil.tcpChannelMap.put(getIPString(ctx), ctx.channel());
            cd.setBotIP(ip);
            cacheUtil.UpdateComputerData(cd);
            return true;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    private void MessageWebClient() {
        WebSocketCommand command = new WebSocketCommand();
        command.setComponentName("bots");
        command.setCommandType("fresh");
        for (Channel channel : GlobalUserUtil.socketChannels) {
            channel.writeAndFlush(new TextWebSocketFrame(JsonToObjectUtil.objectToJson(command)));
        }
    }

    /**
     * websocket消息处理
     *
     * @param ctx
     * @param msg
     */
    private void doHandlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //判断msg 是哪一种类型  分别做出不同的反应
        if (msg instanceof CloseWebSocketFrame) {
            logger.info("【Websocket close】");
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg);
            return;
        }
        if (msg instanceof PingWebSocketFrame) {
            logger.info("【Websocket ping】");
            PongWebSocketFrame pong = new PongWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(pong);
            return;
        }
        if (msg instanceof PongWebSocketFrame) {
            logger.info("【Websocket pong】");
            PingWebSocketFrame ping = new PingWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(ping);
            return;
        }
        if ((msg instanceof TextWebSocketFrame)) {
            String message = ((TextWebSocketFrame) msg).text();
            logger.info("WebSocket:::" + message);
        }
    }


    /**
     * wetsocket第一次连接握手
     *
     * @param ctx
     * @param msg
     */
    private void doHandlerHttpRequest(ChannelHandlerContext ctx, HttpRequest msg) {
        // http 解码失败
        if (!msg.getDecoderResult().isSuccess() || (!"websocket".equals(msg.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, (FullHttpRequest) msg, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        //可以获取msg的uri来判断
        String uri = msg.getUri();
        if (!uri.substring(1).equals(URI)) {
            ctx.close();
            return;
        }
        ctx.attr(AttributeKey.valueOf("type")).set(uri);
        //可以通过url获取其他参数
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://" + msg.headers().get("Host") + "/" + URI + "", null, false
        );
        handshaker = factory.newHandshaker(msg);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }
        //进行连接
        handshaker.handshake(ctx.channel(), (FullHttpRequest) msg);
        //可以做其他处理
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }


    /**
     * 获取client对象：ip+port
     *
     * @param ctx
     * @return
     */
    public String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }

    /**
     * 获取client的ip
     *
     * @param ctx
     * @return
     */
    public String getIPString(ChannelHandlerContext ctx) {
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        return socketString.substring(1, colonAt);
    }


}
