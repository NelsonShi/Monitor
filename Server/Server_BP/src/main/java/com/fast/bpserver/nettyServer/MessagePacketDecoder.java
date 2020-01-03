package com.fast.bpserver.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.Buffer;
import java.util.List;

/**
 * Created by Nelson on 2019/12/31.
 * tcp/ip协议解码器 解析发送过来的数据
 */
public class MessagePacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        try {
            while (true) {
                buffer.markReaderIndex();
                int length = buffer.readIntLE();
                if (buffer.readableBytes() < length) {
                    buffer.resetReaderIndex();
                    break;
                } else if (buffer.readableBytes() > length) {
                    byte[] bytesReady = new byte[length];
                    buffer.readBytes(bytesReady);
                    out.add(bytesReady);
                    break;
                } else {
                    byte[] bytesReady = new byte[buffer.readableBytes()];
                    buffer.readBytes(bytesReady);
                    out.add(bytesReady);
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public int byte2int(byte[] res) {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

}
