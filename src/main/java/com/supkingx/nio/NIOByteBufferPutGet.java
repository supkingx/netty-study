package com.supkingx.nio;

import java.nio.ByteBuffer;

/**
 * @description: ByteBuffer 支持类型化的 put 和 get，put 放入的是什么数据类型，get 就应该使用相应的数据类型来取出，否则可能有 BufferUnderflowException 异常。
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        
        // 类型化放入数据
        byteBuffer.putInt(100);
        byteBuffer.putLong(9);
        byteBuffer.putChar('s');
        byteBuffer.putShort((short) 4);
        
        // 取出
        byteBuffer.flip();
        System.out.println();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
//        System.out.println(byteBuffer.getShort());
        // 这会报错 BufferUnderflowException
        System.out.println(byteBuffer.getLong());
    }
}
