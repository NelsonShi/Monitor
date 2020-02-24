package com.fast.monitorserver.RSListenerClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by Nelson on 2020/1/9.
 */
public class NettyClient extends Thread{
    private String host;
    // Netty服务端监听端口号
    private int port;
    // 通信管道
    private Channel channel;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param messsage
     */
    public void sendMessage(String messsage) {
        if (channel != null && channel.isActive()) {
            String result=messsage+"\r";
            channel.writeAndFlush(result);
        }
    }

    /**
     * 开启客户端
     *
     * @throws Exception
     */
    private void startClient() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // NIO客户端启动辅助类，降低客户端开发复杂度
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    // 当执行channelfactory的newChannel方法时,会创建NioSocketChannel实例
                    .channel(NioSocketChannel.class)
                    // ChannelOption.TCP_NODELAY参数对应于套接字选项中的TCP_NODELAY,该参数的使用与Nagle算法有关
                    // Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次,因此在数据包不足的时候会等待其他数据的到了，组装成大的数据包进行发送，虽然该方式有效提高网络的有效
                    // 负载，但是却造成了延时，而该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输，于TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量最大的时候，一次性发送
                    // 数据，适用于文件传输。
                    .option(ChannelOption.TCP_NODELAY, true)
                    // handler()和childHandler()的主要区别是，handler()是发生在初始化的时候，childHandler()是发生在客户端连接之后
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            ByteBuf buf = Unpooled.copiedBuffer("\r".getBytes());
                            // Tcp粘包处理，添加一个特殊字符解码器解码器，它会在解码时按照特殊字符解码
                            pipeline.addLast("frameDecoder",new DelimiterBasedFrameDecoder(1024*1024, buf));
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            // 消息处理handler
                            pipeline.addLast("handler", new NettyClientHandler());
                        }
                    });
            // 发起异步连接操作
            ChannelFuture f = bootstrap.connect(host, port).sync();
            channel = f.channel();
            System.out.println("客户端已启动");
            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接失败，服务端是否开启？");
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }
}
