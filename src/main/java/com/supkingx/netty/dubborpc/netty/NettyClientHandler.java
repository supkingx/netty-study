package com.supkingx.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/6
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context; // 上下文
    private String result; // 返回的结果
    private String param; // 客户端调用方法时，传入参数

    /**
     * (1)
     * 与服务器的链接创建后，就会被调用，这个方法被第一个调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler channelActive 被调用");
        context = ctx; // 在其他方法会使用到 ctx
    }

    /**
     * (4)
     * 收到服务器的数据后，调用方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("NettyClientHandler channelRead 被调用");
        result = msg.toString();
        notify(); // 唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("NettyClientHandler exceptionCaught 被调用");
        cause.printStackTrace();
        ctx.close();

    }

    /**
     * （3）--- 被唤醒后 --> (5)
     * 被 代理对象调用，发送数据给服务器，--> wait --> 等待被唤醒(channelRead) --> 返回结果
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("NettyClientHandler call(1) 被调用");
        context.writeAndFlush(param);
        // 等待 channelRead 方法获取到服务器等结果后，唤醒
        wait();
        // 服务器返回的结果
        System.out.println("NettyClientHandler call(2) 被调用");
        return result;
    }

    /**
     * (2)
     *
     * @param param
     */
    void setParam(String param) {
        System.out.println("NettyClientHandler setParam 被调用");
        this.param = param;
    }
}
