package com.supkingx.netty.dubborpc.netty;

import com.supkingx.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description: 服务器这边的handler 比较简单
 * @Author: wangchao
 * @Date: 2022/1/6
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息，并调用服务
        System.out.println("msg" + msg);
        // 客户端在调用服务器的 api 时，我们需要定义一个协议
        // 比如我们要求 每次发消息都必须以某个字符串开头 "HelloService#hello#"
        if(msg.toString().startsWith("HelloService#hello#")){
            // 服务端调用 服务端的方法
            String response = (new HelloServiceImpl()).hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
