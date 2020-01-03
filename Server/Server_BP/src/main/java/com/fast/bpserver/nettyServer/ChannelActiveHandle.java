package com.fast.bpserver.nettyServer;

import com.fast.bpserver.entity.postEntity.WebSocketCommand;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.JsonToObjectUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/***
 *@Description 心跳-连接-断开连接 处理
 *@Author xinyu.huang
 *@Time 2019/11/30 23:38
 */
@Component
@ChannelHandler.Sharable
public class ChannelActiveHandle extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ChannelActiveHandle.class);

    private static ChannelActiveHandle channelActiveHandle = new ChannelActiveHandle();

    public static ChannelActiveHandle getInstance() {
        return channelActiveHandle;
    }
    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.debug("心跳检测", ctx.channel().toString());
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
//            PingWebSocketFrame ping = new PingWebSocketFrame();
            if (state == IdleState.READER_IDLE) {
                // 在规定时间内没有收到客户端的上行数据, 主动向客户端发送ping  未回应则断开
//             ChannelFuture channelFuture= ctx.writeAndFlush(ping);
             logger.info("在规定时间内没有收到客户端的上行数据");
             ctx.disconnect();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }


    /**
     * channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("Client Active ，{}", ctx.channel().remoteAddress());
    }


    /**
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        RemoveMapChannel(ctx);
        GlobalUserUtil.socketChannels.remove(ctx.channel());
        ctx.disconnect();
    }

    @Override
    public void  exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("引擎 {} 的通道发生异常，即将断开连接", getIPString(ctx));
        RemoveMapChannel(ctx);
        ctx.close();
        ctx.channel().disconnect();
    }

    private void RemoveMapChannel(ChannelHandlerContext ctx){
        String ipString = getIPString(ctx);
        Map<String,Channel> map=GlobalUserUtil.tcpChannelMap;
        if(map.containsKey(ipString)){
            map.remove(ipString);
            cacheUtil.DeleteComputerData(ipString);
            WebSocketCommand command=new WebSocketCommand();
            command.setComponentName("bots");
            command.setCommandType("fresh");
            for (Channel channel : GlobalUserUtil.socketChannels) {
                channel.writeAndFlush(new TextWebSocketFrame(JsonToObjectUtil.objectToJson(command)));
            }
        };
    }

    public String getIPString(ChannelHandlerContext ctx) {
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        return socketString.substring(1, colonAt);
    }
}

