package com.fast.monitorserver.RSListenerClient;


import com.fast.bpserver.entity.BPAInternalAuth;
import com.fast.bpserver.entity.BPASession;
import com.fast.bpserver.service.impl.IBPAInternalAuthImpl;
import com.fast.bpserver.service.impl.IBPASessionImpl;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.SpringContextUtil;
import com.fast.monitorserver.entity.RsPcUser;
import com.fast.monitorserver.service.impl.RsPcUserImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 处理客户端的channel
 *
 * @author lucher
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {
    private static Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, Object value) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String ip = getRemoteIp(ctx);
        logger.info("client channelActive,ip={},begin validate", ip);
        InitConnection(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            String response = (String) msg;
            logger.info("ResourcePc Client receive response:" + response);
            //以下是初始化链接时与服务端的验证
            ValidateConnnect(ctx,response);
            //启动session
//            StartSession(ctx,response);
            //记录PC 回传值
            RecordMessage(ctx,response);
        } finally {

        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String ip=getRemoteIp(ctx);
        ReMovePcClient(ip);
        logger.info("client channelInactive,ip"+ip);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.info("Resource PC Client catch Exception:"+cause.getMessage());
        String ip=getRemoteIp(ctx);
        ReMovePcClient(ip);
    }

    private void ReMovePcClient(String ip) {
        RsPcClient.tcpClientMap.remove(ip);
    }

    private String getRemoteIp(ChannelHandlerContext ctx) {
        String address = ctx.channel().remoteAddress().toString();
        String ip = address.substring(1, address.indexOf(":"));
        return ip;
    }

    private void InitConnection(Channel channel){
        channel.writeAndFlush("version\r");
    }

    private void ValidateConnnect(ChannelHandlerContext ctx,String response){
        if(response.toUpperCase().contains("AUTOMATE")){
            RsPcUserImpl rsPcUserService=SpringContextUtil.getBean("rsPcUserImpl",RsPcUserImpl.class);
            IBPAInternalAuthImpl ibpaInternalAuthService=SpringContextUtil.getBean("IBPAInternalAuthImpl",IBPAInternalAuthImpl.class);
            RsPcUser user=rsPcUserService.findUser();
            BPAInternalAuth bpaInternalAuth=ibpaInternalAuthService.GenrateInternalAuth(user.getUserId(),null);
            Channel channel=ctx.channel();
            channel.writeAndFlush("internalauth "+bpaInternalAuth.getUserId()+"_"+bpaInternalAuth.getToken()+"\r");
        }else if(response.toUpperCase().contains("ACCEPTED")){
            String ip = getRemoteIp(ctx);
            RsPcClient.tcpClientMap.put(ip, ctx.channel());
            logger.info("ResourcePc Client validate success,ip={}",ip);
        }
    }

    private void StartSession(ChannelHandlerContext ctx,String response){
        if(response.toUpperCase().contains("PARAMETERS SET")){
            String resourceId="072A4CC3-B52D-49B0-ADD0-9BDF326CCB54";
            IBPASessionImpl ibpaSessionService=SpringContextUtil.getBean("IBPASessionImpl",IBPASessionImpl.class);
            BPASession bpaSession=ibpaSessionService.findPendingSession(resourceId);
            if(bpaSession==null){
                logger.info("Start session failed,no pending on resource PC，id={}",resourceId);
                return;
            }
            IBPAInternalAuthImpl ibpaInternalAuthService=SpringContextUtil.getBean("IBPAInternalAuthImpl",IBPAInternalAuthImpl.class);
            BPAInternalAuth bpaInternalAuth=ibpaInternalAuthService.GenrateInternalAuth(bpaSession.getStarteruserid(),bpaSession.getProcessid());
            if(bpaInternalAuth==null){
                logger.info("Start session failed,generate BPAInternalAuth fail，id={}",resourceId);
                return;
            }
            Channel channel=ctx.channel();
            String message="startas "+bpaInternalAuth.getUserId()+"_"+bpaInternalAuth.getToken()+" "+bpaSession.getSessionid()+"\r";
            channel.writeAndFlush(message);
        }
    }

    /**
     * 将客户端传来的信息按照IP分类记录在缓存中(只记录最近的)
     */
    private void RecordMessage(ChannelHandlerContext ctx,String response){
        if(response.toUpperCase().contains("PONG"))return;
        String ipAddress=getRemoteIp(ctx);
        CacheUtil cacheUtil=SpringContextUtil.getBean("cacheUtil",CacheUtil.class);
        Map<String,String> messageMap=cacheUtil.getPCMessageMap();
        long time=System.currentTimeMillis();
        messageMap.put(ipAddress,time+"_"+response);
    }
}