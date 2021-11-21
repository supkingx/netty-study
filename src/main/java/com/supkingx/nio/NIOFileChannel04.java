package com.supkingx.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class NIOFileChannel04 {
    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("123.jpg");
             FileOutputStream fileOutputStream = new FileOutputStream("king.jpg");
             FileChannel inputChannel = fileInputStream.getChannel();
             FileChannel outChannel = fileOutputStream.getChannel()) {

            // 从 inputChannel 拷贝数据到 outChannel 中去
            outChannel.transferFrom(inputChannel, 0, inputChannel.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
