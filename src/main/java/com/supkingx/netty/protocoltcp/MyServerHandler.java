package com.supkingx.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/5
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        // 接收到数据 并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到信息如下");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content, StandardCharsets.UTF_8));
        System.out.println("服务器接收到消息包数量 " + (++this.count) + "\n");

        // 回复消息
        String responseContent = UUID.randomUUID().toString();
        byte[] responseContentBytes = responseContent.getBytes(StandardCharsets.UTF_8);
        int length = responseContentBytes.length;
        // 构建一个协议包
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setContent(responseContentBytes);
        messageProtocol.setLen(length);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
