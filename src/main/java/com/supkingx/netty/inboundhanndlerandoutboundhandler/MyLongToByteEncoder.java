package com.supkingx.netty.inboundhanndlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @description: 进行编码 long to byte ( outbound )
 * @Author: wangchao
 * @Date: 2022/1/3
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    /**
     * 编码的方法
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder encode 被调用");
        System.out.println("msg=" + msg);
        out.writeLong(msg);
    }
}
