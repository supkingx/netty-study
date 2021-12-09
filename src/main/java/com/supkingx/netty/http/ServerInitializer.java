package com.supkingx.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/12/9
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个 netty 提供的 httpServerCodec codec =》【coder - decoder】
        // HttpServerCodec 说明
        // 1、HttpServerCodec 是 netty 提供的处理 http 的编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 2、增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler", new HttpServerHandler());
    }
}
