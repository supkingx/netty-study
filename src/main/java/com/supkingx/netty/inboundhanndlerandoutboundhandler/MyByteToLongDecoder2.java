package com.supkingx.netty.inboundhanndlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/3
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        // 因为 Long 8个字节，必须判断有八个字节才能读取一个Long
        out.add(in.readLong());

        // 这步不需要了
//        if (in.readableBytes() >= 8) {
//            out.add(in.readLong());
//        }
    }
}
