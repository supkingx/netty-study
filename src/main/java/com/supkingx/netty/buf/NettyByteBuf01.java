package com.supkingx.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/12/15
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        // 创建一个ByteBuf
        // 说明 1、对象，该对象包含一个数组 arr，是一个byte[10]
        // 在 netty 的 buffer 中不需要使用 flip 进行反转
        // 底层维护了一个 readerindex  和 writerIndex 和 capacity 将 buffer分成三个区
        // 0---readerindex 已经读取的区域
        // readerindex---writerIndex ， 可读的区域
        // writerIndex -- capacity, 可写的区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        System.out.println("capacity=" + buffer.capacity());
        // 输出
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }

        System.out.println("----");
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
    }
}
