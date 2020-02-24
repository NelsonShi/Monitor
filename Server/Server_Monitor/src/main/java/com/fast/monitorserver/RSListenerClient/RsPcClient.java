package com.fast.monitorserver.RSListenerClient;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Nelson on 2020/1/10.
 */
public class RsPcClient {
    public static Map<String, Channel> tcpClientMap = new ConcurrentHashMap<String, Channel>();
}
