package com.fast.monitorserver.schedules;


import com.fast.monitorserver.RSListenerClient.RsPcClient;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 * Created by Nelson on 2020/1/10.
 * Resource PC TCP 客户端心跳
 */
@Component
public class ResourcePcClientHeartBeat {
    private static Logger log= LoggerFactory.getLogger(ResourcePcClientHeartBeat.class);

    @Scheduled(initialDelay=1000,fixedDelay=10000)
    private void run(){
        for (Channel channel: RsPcClient.tcpClientMap.values()){
             channel.writeAndFlush("ping\r");
        }
    }
}
