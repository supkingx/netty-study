package com.supkingx.netty.inboundhanndlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/3
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 加入一个出站的 handler 对数据进行一个编码
        pipeline.addLast(new MyLongToByteEncoder());
        
        // 入站的 handler 进行解码，MyByteToLongDecoder
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());

        // 加入一个自定义的 handler，处理业务逻辑
        pipeline.addLast(new MyClientHandler());
    }
}
