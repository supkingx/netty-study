package com.supkingx.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/5
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        // 将buffer转化成字符串
        String message = new String(buffer, StandardCharsets.UTF_8);
        System.out.println("服务器端接收到数据 " + message);
        System.out.println("服务器端接收到消息次数 " + (++this.count));

        // 服务器会送数据给客户端，会送一个随机Id
        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString()+" ", StandardCharsets.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
