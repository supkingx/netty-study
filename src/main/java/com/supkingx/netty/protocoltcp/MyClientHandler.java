package com.supkingx.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/5
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送10条数据 "今天天气冷，吃火锅" 编号
        for (int i = 0; i < 5; i++) {
            String msg = "你好呀。。。";
            byte[] content = msg.getBytes(StandardCharsets.UTF_8);
            int length = content.length;

            // 创建协议包
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        String message = new String(content, StandardCharsets.UTF_8);
        System.out.println("客户端接收到消息长度=" + len);
        System.out.println("客户端接收到消息内容=" + message);
        System.out.println("客户端接收到消息数量=" + (++this.count) + "\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("异常信息=" + cause.getMessage());
        ctx.close();
    }
}
