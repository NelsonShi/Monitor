package com.fast.bpserver.nettyServer;

import com.fast.bpserver.entity.postEntity.BotCommand;
import com.fast.bpserver.entity.postEntity.WebSocketCommand;
import com.fast.bpserver.utils.JsonToObjectUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

/**
 * Created by Nelson on 2019/12/31.
 */
@Service
public class GlobalNoticeUtil {
    public void NoticeAllWebClient(WebSocketCommand command){
        for (Channel channel : GlobalUserUtil.socketChannels) {
            channel.writeAndFlush(new TextWebSocketFrame(JsonToObjectUtil.objectToJson(command)));
        }
    }

    public void NoticeAllTCPClient(BotCommand command){
        for (Channel channel:GlobalUserUtil.tcpChannelMap.values()){
            channel.writeAndFlush(JsonToObjectUtil.objectToJson(command));
        }
    }

    public void NoticeTCPClientWithIP(String ip,BotCommand command){
        Channel channel=GlobalUserUtil.tcpChannelMap.get(ip);
        if(channel!=null){
            channel.writeAndFlush(JsonToObjectUtil.objectToJson(command));
        }
    }
}
