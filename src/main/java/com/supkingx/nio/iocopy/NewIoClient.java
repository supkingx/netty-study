package com.supkingx.nio.iocopy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/24
 */
public class NewIoClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        FileChannel fileChannel = new FileInputStream("/Users/superking/Desktop/test1.txt").getChannel();
        long startTime = System.currentTimeMillis();

        // 在linux下的 transferTo 方法就可以完成传输
        // 在windows 下 一次调用 transferTo 只能发送 8m，需要分段传输文件
        // transferTo底层使用了零拷贝
        long count = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数=" + count + ",耗时：" + (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
