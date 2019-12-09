package com.blueprismserver.commandLineRunner;


import com.blueprismserver.utils.CacheUtil;
import com.blueprismserver.utils.TCPServer.NettyServer;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Created by Nelson on 2019/10/24.
 * 项目启动后执行的代码，可以完成一些自定义设置，目前为加载缓存
 * Spring Beans都初始化之后SpringApplication.run()之前执行
 *
 */
@Component
@Order(value=1)
public class Cachelistener implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Cachelistener.class);
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private NettyServer nettyServer;
    @Override
    public void run(String... args) throws Exception {
         log.info("--------------------- init system cache -------------------------");
         cacheUtil.InitCache();
         log.info("---------------------cache init completed ------------------------");
         log.info("---------------------start TCP server -------------------------");
         ChannelFuture start = nettyServer.start();
         log.info("---------------------TCP server started---------------------");
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
         start.channel().closeFuture().syncUninterruptibly();

    }
}
