package com.supkingx.nio;

import java.nio.ByteBuffer;

/**
 * @description: 2. 可以将一个普通 Buffer 转成只读 Buffer
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        for (int i = 0; i <64; i++) {
            byteBuffer.put((byte) i);
        }
        
        byteBuffer.flip();
        
        // 得到一个只读 buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
        
        // 读取
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        
        // 报错 ReadOnlyBufferException
        readOnlyBuffer.put((byte) 100);
    }
}
