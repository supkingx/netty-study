package com.supkingx.nio;

import java.nio.IntBuffer;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //举例说明 Buffer 的使用(简单说明)
        //创建一个 Buffer，大小为 5，即可以存放 5 个 int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向 buffer 中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
//        intBuffer.put(0,1);
        // 从 buffer 读取数据
        // 将 buffer 转换，读写切换(!!!)，其实就是将 position 置为0，从头开始
        intBuffer.flip();
        
//        intBuffer.position(1);
//        intBuffer.limit(3);

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
