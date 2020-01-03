package com.fast.bpserver.tcpServer;

import com.fast.bpserver.entity.vo.ComputerData;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.JsonToObjectUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


/**
 * description:
 * author:
 * date:
 **/
@Component
@ChannelHandler.Sharable
public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {
    @Autowired
    private CacheUtil cacheUtil;
    private static final Logger log = LoggerFactory.getLogger(ServerChannelHandler.class);
    /**
     * 接收到Client 传来的数据后 触发的事件
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)  {
//        System.out.println("Netty tcp server receive msg : " + msg);
        //response to client
        //ctx.channel().writeAndFlush(" response msg ").syncUninterruptibly();
        try {
            if(ctx==null||msg==null)return;
            ComputerData cd= JsonToObjectUtil.jsonToPojo(msg.toString(),ComputerData.class);
            if(cd==null)return;
            String ip=getIPString(ctx);
            if(!NettyServer.map.containsKey(ip)) NettyServer.map.put(getIPString(ctx), ctx.channel());
            cd.setBotIP(ip);
            cacheUtil.UpdateComputerData(cd);
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info(msg.toString());
    }



    /**
     * 活跃的、有效的通道
     * 第一次连接成功后进入的方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx)  {
        try{
        super.channelActive(ctx);
        log.info("tcp client " + getRemoteAddress(ctx) + " connect success");
        //往channel map中添加channel信息
        NettyServer.map.put(getIPString(ctx), ctx.channel());}catch (Exception e){
            log.info(e.getMessage());
        }
    }

    /**
     * 不活动的通道
     * 连接丢失后执行的方法（client端可据此实现断线重连）
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try{
        log.info("tcp client " + getRemoteAddress(ctx) + " 链接断开，即将删除链接");
        //删除Channel Map中的失效Client
        NettyServer.map.remove(getIPString(ctx));
        ctx.close();
        cacheUtil.DeleteComputerData(getIPString(ctx));}catch (Exception e){
            log.info(e.getMessage());
        }
    }

    /**
     * 异常处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try{
        //发生异常，关闭连接
        log.error("引擎 {} 的通道发生异常，即将断开连接", getRemoteAddress(ctx));
        ctx.close();//再次建议close
        cacheUtil.DeleteComputerData(getIPString(ctx));}
        catch (Exception e){
            log.info(e.getMessage());
        }
    }



    /**
     * 心跳机制，超时处理
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        try{
        String ipString=getIPString(ctx);
        cacheUtil.DeleteComputerData(ipString);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("Client: " + ipString + " READER_IDLE 读超时");
                ctx.disconnect();//断开
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("Client: " + ipString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("Client: " + ipString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }}catch (Exception e){
            log.info(e.getMessage());
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
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }


}
