package com.supkingx.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/12/18
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    // 定义一个channel组，管理所有的channel
//    GlobalEventExecutor.INSTANCE 全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 使用一个 hashMap 管理 ；单聊的时候 可以用这个，后面自己扩展
//    public static Map<String, Channel> channelMap = new HashMap<>();

    /**
     * 表示连接建立，一旦连接，第一个被执行
     * 将当前 channel 加入到 channelGroup
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息推送给其它在线的客户端
        /**
         * 该方法会将 channelGroup 中所有的 channel 遍历，并发送 消息
         * 我们不需要自己遍历
         */
        channelGroup.writeAndFlush("[客户端]" + LocalDateTime.now().format(dtf) + " " + channel.remoteAddress() + " 加入聊天");
        channelGroup.add(channel);
        
        // 单聊的时候 可以用这个
//        channelMap.put("id100", channel);
    }

    /**
     * 表示 断开连接，将 xx客户离开信息推送给当前在线的客户
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + LocalDateTime.now().format(dtf) + " " + channel.remoteAddress() + " 离开了");
        System.out.println("channelGroupSize is " + channelGroup.size());
    }

    /**
     * 表示 channel 处于活动状态，提示 xx 上线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now().format(dtf) + " " + ctx.channel().remoteAddress() + " 上线了~");

    }

    /**
     * 表示 channel 处于不活跃状态，提示 xx 下线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now().format(dtf) + " " + ctx.channel().remoteAddress() + " 离线了~");
    }

    /**
     * 读取数据并执行业务操作
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取到当前 channel 
        Channel channel = ctx.channel();

        // 遍历 channelgroup,根据不同的情况，回送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                // 不是当前的 channel，转发消息 （不发给自己）
                ch.writeAndFlush("[客户]" + LocalDateTime.now().format(dtf) + " " + channel.remoteAddress() + "发送了消息:" + msg);
            } else {
                // 发给自己
                ch.writeAndFlush("[自动发送了消息]" + sdf.format(new Date()) + " " + msg);
            }
        });
    }

    /**
     * 异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("【服务器异常】" + LocalDateTime.now().format(dtf) + " " + ctx.toString());
        cause.printStackTrace();
        ctx.close();
    }
}
