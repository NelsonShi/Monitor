package com.fast.bpserver.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * Created by Nelson on 2019/12/20.
 *Netty Server 同时支持tcp/ip  websocket协议
 * 用于消息推送
 */
@Component
public class NettyDeviceServer {
    private static Logger logger = LoggerFactory.getLogger(NettyDeviceServer.class);

    @Resource
    private NettyChannelHandler nettyChannelHandler;
    @Resource
    private ChannelActiveHandle channelActiveHandle;
    @Value("${netty.tcp.server.port}")
    private Integer port;

    /**
     * 存储client的channel
     * key:ip，value:Channel
     */
    public static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();

    //配置服务端线程组
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();
    ChannelFuture socketfuture = null;

    @PreDestroy             //关闭spring容器后释放资源
    public void stop(){
        if(socketfuture!=null){
            socketfuture.channel().close().addListener(ChannelFutureListener.CLOSE);
            socketfuture.awaitUninterruptibly();
            socketfuture=null;
            logger.info("Netty 服务端关闭");
        }

        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    /**
     * 启动流程
     */
    private void start() throws InterruptedException {
        logger.info("---------------------Start Netty server ---------------------");
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_REUSEADDR, true) //快速复用端口
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //Socket 连接心跳检测
                            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(2, 0, 0, TimeUnit.MINUTES));

                            ch.pipeline().addLast("socketChoose",new SocketChooseHandler());
                            ch.pipeline().addLast("active",channelActiveHandle);
//                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,0));
                            ch.pipeline().addLast(new MessagePacketDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast("commonhandler",nettyChannelHandler);

                        }
                    });

            //绑定端口，同步等待成功
            socketfuture = serverBootstrap.bind(port).sync();
            if(socketfuture.isSuccess()){
                logger.info("---------------------Netty server started---------------------");
            }

            socketfuture.channel().closeFuture().sync();

        }catch (Exception e){
            //优雅退出，释放线程池
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }finally {
            //优雅退出，释放线程池
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
         return;
    }

    @PostConstruct()
    public void init(){
        //需要开启一个新的线程来执行netty server 服务器
        new Thread(new Runnable() {
            public void run() {
                try {
                    start();
                }catch (Exception e){
                    logger.info("Netty start error:"+e.getMessage());
                }

            }
        }).start();
    }
}
