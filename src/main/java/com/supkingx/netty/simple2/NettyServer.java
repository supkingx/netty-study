package com.supkingx.netty.simple2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description:
 *   ○ public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)，该方法用于服务器端，用来设置两个 EventLoop
 *   ○ public B group(EventLoopGroup group)，该方法用于客户端，用来设置一个 EventLoopGroup
 *   ○ public B channel(Class<? extends C> channelClass)，该方法用来设置一个服务器端的通道实现
 *   ○ public <T> B option(ChannelOption<T> option, T value)，用来给 ServerChannel 添加配置
 *   ○ public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value)，用来给接收到的通道添加配置
 *   ○ public ServerBootstrap childHandler(ChannelHandler childHandler)，该方法用来设置业务处理类（自定义的handler）
 *   ○ public ChannelFuture bind(int inetPort)，该方法用于服务器端，用来设置占用的端口号
 *   ○ public ChannelFuture connect(String inetHost, int inetPort)，该方法用于客户端，用来连接服务器端
 * @Author: wangchao
 * @Date: 2021/12/6
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建 BossGroup 和 WorkerGroup
        // 说明
        // 1. 创建两个线程组 bossGroup 和 workerGroup
        // 2. bossGroup 只是处理连接请求，真正的和客户端业务处理，会交给 workerGroup 完成
        // 3. 两个都是无限循环
        // 4. bossgroup 和 workGroup 含有的子线程（NIOEventGroup）的个数 默认是实际cpu的核数*2
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 12核cpu，默认是24个线程 NettyRuntime.availableProcessors() * 2
//        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 方便测试，设置为 3 个线程
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(3);

        try {
            // 创建服务器端的启用对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup)// 设置两个现场组
                    .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 用来给接收到的通道添加配置 设置保持活动连接状态
                    .handler(null) // 对 bossGroup 进行配置业务处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 对workerGroup 创建一个通道测试对象（匿名）
                        // 给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 在管道的最后添加一个处理器(自定义的)
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给我们的workerGroup 的 EventLoop 对应的管道设置处理器
            System.out.println(".....服务器 is ready");

            // 绑定一个端口并且同步，生成一个 channelFuture 对象
            // 启动服务器并绑定端口
            ChannelFuture cf = bootstrap.bind(6677).sync();
            
            // 给cf 注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口 6677 成功");
                    } else {
                        System.out.println("监听端口 6677 失败");
                    }
                }
            });

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
