package com.supkingx.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/12/6
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {

        // 客户端需要一个事件循环跑
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {

            // 创建客户端启动对象
            // 注意客户端使用的不是 serverBootstrap 而是 BootStrap
            Bootstrap bootstrap = new Bootstrap();

            // 设置相关参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 在管道的最后添加一个处理器(自定义的)
                            channel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端。。ok");

            // 启动客户端去连接服务器端
            // 关于 channelFuture 要分析，涉及到 netty 的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6677).sync();
            // 给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅的关闭
            group.shutdownGracefully();
        }
    }
}
