package com.supkingx.nio;

import java.nio.ByteBuffer;

/**
 * @description: 比较创建 DirectByteBuffer 和 HeapByteBuffer 哪个快
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class DirectAndHeapSpeedCompare {
    public static void main(String[] args) {
        int length=1000000;
        directExecuteTime(length);
        heapExecuteTime(length);
    }

    private static void directExecuteTime(int length) {
        long startTime = System.currentTimeMillis();
        ByteBuffer[] byteBufferArray = new ByteBuffer[length];
        for (int i = 0; i < length; i++) {
            byteBufferArray[i] = ByteBuffer.allocateDirect(1024);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("创建" + length + "个DirectByteBuffer所消耗的时间：" + (endTime - startTime));
    }

    private static void heapExecuteTime(int length) {
        long startTime = System.currentTimeMillis();
        ByteBuffer[] byteBufferArray = new ByteBuffer[length];
        for (int i = 0; i < length; i++) {
            byteBufferArray[i] = ByteBuffer.allocate(1024);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("创建" + length + "个HeapByteBuffer所消耗的时间：" + (endTime - startTime));
    }
}
