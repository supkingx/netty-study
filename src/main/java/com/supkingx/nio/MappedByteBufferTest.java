package com.supkingx.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: NIO 还提供了 MappedByteBuffer，可以让文件直接在内存（堆外的内存）中进行修改，而如何同步到文件 则由 NIO 来完成。
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("file01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数 1：FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数 2：0 可以直接修改的起始位置
         * 参数 3：5 是映射到内存的大小（不是索引位置），即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-4 (put(5,xx)就会报错IndexOutOfBoundsException)
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) '7');
        mappedByteBuffer.put(3, (byte) '9');
//        mappedByteBuffer.put(5,(byte) '9');
 
        channel.close();
        randomAccessFile.close();
    }
}
