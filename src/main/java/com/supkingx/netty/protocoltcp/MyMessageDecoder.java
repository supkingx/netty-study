package com.supkingx.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @description: 解码器
 * @Author: wangchao
 * @Date: 2022/1/5
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");
        // 需要将得到的二进制字节码 -> MessageProtocol 数据包对象
        int length = in.readInt();
        byte[] content = new byte[length];
        // 获取内容
        in.readBytes(content);

        // 封装成 MessageProtocol 对象 ，放入out，传递给下一个 handler 业务处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}
