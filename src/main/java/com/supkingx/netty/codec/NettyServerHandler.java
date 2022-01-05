package com.supkingx.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * 说明
 * 1、我们自定义一个 Handler 需要继续netty 规定好的谋和 handlerAdapter(规范)
 * 2、这时我们自定义一个Handler，才能称为一个handler
 *
 * @description:
 * @Author: wangchao
 * @Date: 2021/12/6
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取数据实际（这里我们可以继续读取客户端发送的消息）

    /**
     * @param ctx 上下文对象，含有 管道pipeline，通道channel，地址
     * @param msg 就是客户端发送的数据 默认Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Thread.sleep(10 * 1000);
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端，你好！", CharsetUtil.UTF_8));
        } catch (InterruptedException e) {
            System.out.println("发生异常" + e.getMessage());
        }
        
        
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        System.out.println("server ctx=" + ctx);
        System.out.println("看看channel 和 pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();


        // 将 msg 转化成一个 ByteBuffer
        // ByteBuf 是 Netty 提供的，不是NIO 的 ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     * 可以在这里向通道写回数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 是 write + flush
        // 将数据写入到缓存并刷新
        // 一般讲，我们对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,client", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务端异常了");
        cause.printStackTrace();
        ctx.close();
    }
}
