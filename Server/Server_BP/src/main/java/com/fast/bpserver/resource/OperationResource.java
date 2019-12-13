package com.fast.bpserver.resource;

import com.fast.bpserver.entity.vo.BotCommand;
import com.fast.bpserver.entity.vo.BotCommandResult;
import com.fast.bpserver.tcpServer.NettyServer;
import com.fast.bpserver.utils.JsonToObjectUtil;
import io.netty.channel.Channel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Nelson on 2019/12/11.
 */
@RestController
@RequestMapping("/operation")
public class OperationResource {

    @RequestMapping(value = "/command",method = RequestMethod.POST)
    public BotCommandResult Command(@RequestBody BotCommand vo, HttpServletRequest request){
        Map<String,Channel> chanelMap= NettyServer.map;
        Channel channel=chanelMap.get(vo.getIp());
        BotCommandResult br;
        if(channel==null){
            br=new BotCommandResult(0,"false");
        }else {
            channel.write(JsonToObjectUtil.objectToJson(vo));
            channel.flush();
            br=new BotCommandResult(1,"success");
        }
        return br;
    }
}
