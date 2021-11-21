package com.supkingx.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class NIOFileChannel03 {
    public static void main(String[] args) {

        try (FileInputStream fileInputStream = new FileInputStream("file02.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("file022.txt")) {

            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outChannel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            while (true) {
                // 这里有一个重要的操作
                byteBuffer.clear(); // 清空 buffer
//                public final Buffer clear() {
//                    position = 0;
//                    limit = capacity;
//                    mark = -1;
//                    return this;
//                }

                int readLen = inputChannel.read(byteBuffer);
                System.out.println("readLen=" + readLen);
                if (readLen == -1) {
                    break;
                }
                // 将 buffer 写入到 outChannel
                byteBuffer.flip();
                outChannel.write(byteBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
