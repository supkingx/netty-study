package com.supkingx.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: 1. 使用前面学习后的 ByteBuffer（缓冲）和 FileChannel（通道），将 "hello,supkingx" 写入到 file01.txt 中
 * 2. 文件不存在就创建
 * 3. 代码演示
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class NIOFileChannel01 {
    public static void main(String[] args) {
        String str = "hello,supkingx";
        try (FileOutputStream fileOutputStream = new FileOutputStream("file01.txt")) {
            // 通过 fileOutputStream 获取对应的 FileChannel
            FileChannel fileChannel = fileOutputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
            // 将 Str 放入到 byteBuffer
            byteBuffer.put(str.getBytes());
            // 对 byteBuffer 进行反转 flip
            byteBuffer.flip();
            // 将 byteBuffer中的数据写入到 fileChannel
            fileChannel.write(byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
