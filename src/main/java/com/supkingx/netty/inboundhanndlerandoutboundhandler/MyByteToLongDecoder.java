package com.supkingx.netty.inboundhanndlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description: 进行解码 Byte --> Long (inbound)
 * @Author: wangchao
 * @Date: 2022/1/3
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * decode 会根据接收的数据，被调用多次，直到确定没有新的元素被添加到 list，或者是 ByteBuf 没有更多的可读字节为止
     * 如果 List out 不为空，就会将list 的内容传递给下一个 channelinboundhandler 处理，该处理器的方法也会被调用多次。
     * @param ctx 上下文对象
     * @param in  入站的 ByteBuffer
     * @param out List 集合，将解码后的数据传给下一个 handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        // 因为 Long 8个字节，必须判断有八个字节才能读取一个Long
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
