package com.fast.bpserver.nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Nelson on 2019/12/20.
 */

@Component
public class PipelineAdd {

    public  void websocketAdd(ChannelHandlerContext ctx){
        // HttpServerCodec：将请求和应答消息解码为HTTP消息
        ctx.pipeline().addBefore("commonhandler","http-codec",new HttpServerCodec());
        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
        ctx.pipeline().addBefore("commonhandler","aggregator",new HttpObjectAggregator(65535));
        // ChunkedWriteHandler：向客户端发送HTML5文件,文件过大会将内存撑爆
        ctx.pipeline().addBefore("commonhandler","http-chunked",new ChunkedWriteHandler());
        ctx.pipeline().addBefore("commonhandler","WebSocketAggregator",new WebSocketFrameAggregator(65535));
//        ctx.pipeline().remove("commonhandler");
//        ctx.pipeline().addLast("webSocketHandler",new WebSocketChannelHandler());
    }
}
