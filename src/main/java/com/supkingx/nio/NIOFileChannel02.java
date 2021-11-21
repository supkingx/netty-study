package com.supkingx.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class NIOFileChannel02 {
    public static void main(String[] args) {
        File file = new File("file01.txt");
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            FileChannel fileChannel = fileInputStream.getChannel();
            // 创建缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
            // 将 通道的数据读取到Buffer
            fileChannel.read(byteBuffer);
            // 将 bytebuffer 的字节数据 转成String
            System.out.println(new String(byteBuffer.array()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
