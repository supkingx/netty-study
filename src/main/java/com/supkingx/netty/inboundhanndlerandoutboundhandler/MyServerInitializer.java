package com.supkingx.netty.inboundhanndlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/3
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 入站的 handler 进行解码，MyByteToLongDecoder
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        
        // 出站 handler 进行编码，MyLongToByteEncoder
        pipeline.addLast(new MyLongToByteEncoder());
        
        // 自定义的 handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
