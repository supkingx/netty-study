package com.supkingx.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/12/18
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        // 创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", StandardCharsets.UTF_8);

        // 使用相关的方法
        if (byteBuf.hasArray()) { // true
            byte[] content = byteBuf.array();
            // 将 content 转成字符串
            System.out.println(new String(content, StandardCharsets.UTF_8));
            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset());  // 0
            System.out.println(byteBuf.readerIndex());  // 0
            System.out.println(byteBuf.writerIndex());  // 12
            System.out.println(byteBuf.capacity());  // 36

            System.out.println(byteBuf.readByte());
            System.out.println(byteBuf.getByte(0));

            int len = byteBuf.readableBytes();

            System.out.println("len = " + len);  // 可读取的字节数

            for (int i = 0; i < len; i++) {
                System.out.print((char) byteBuf.getByte(i));
            }

            System.out.println();
            System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8));
        }
    }
}
