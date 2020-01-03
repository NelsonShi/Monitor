package com.fast.bpserver.nettyServer;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Nelson on 2019/12/23.
 */
public class GlobalUserUtil {
    //保存全局的  连接上服务器的客户
    public static ChannelGroup socketChannels = new DefaultChannelGroup(GlobalEventExecutor
            .INSTANCE);
    public static Map<String, Channel> tcpChannelMap = new ConcurrentHashMap<String, Channel>();

}
