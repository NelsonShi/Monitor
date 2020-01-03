package com.fast.bpserver.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Nelson on 2019/12/31.
 * tcp/ip 编码器  当服务端像客户端推送消息时 触发
 */
public class MessagePacketEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception
    {
        try {
//            byte[] result=new byte[8];
//            System.arraycopy((byte[])msg,0,result,0,8);
            //在这之前可以实现编码工作。
            out.writeBytes((byte[])msg);
        }finally {

        }
    }
}
