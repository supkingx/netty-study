package com.supkingx.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @description: // 心跳处理器
 * @Author: wangchao
 * @Date: 2021/12/19
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(3);

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))// 在 bossGroup 增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             * 加入一个 netty 提供 IdleStateHandler
                             * 1、IdleStateHandler 是 netty 提供的处理空闲状态的处理器
                             * 2、Long readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包，检测是否还是连接的状态
                             * 3、Long writerIdleTime：表示多长时间没有写操作，就会发送一个心跳检测包，检测是否还是连接的状态
                             * 4、Long allIdleTime：表示多长时间即没有读也没有写操作，就会发送一个心跳检测包，检测是否还是连接的状态
                             *
                             * 文档说明： 
                             * Triggers an IdleStateEvent when a Channel has not performed read, write, or both operation for a while.
                             *
                             * 5、当 IdleStateEvent 触发后，就会传递给管道的下一个handler去处理，通过调用下一个handler 的 userEventTriggered ，在该方法中去处理
                             *   IdleStateEvent（读空闲，写空闲，读写空闲）
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的 handler （自定义）
                            pipeline.addLast(new MyServerHandler());
                        }
                    });

            // 启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(6699).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
