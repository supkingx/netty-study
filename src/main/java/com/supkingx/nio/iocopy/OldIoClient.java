package com.supkingx.nio.iocopy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/23
 */
public class OldIoClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7001);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("/Users/superking/Desktop/test1.txt"));
        BufferedOutputStream bufferOutPutStream = new BufferedOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[1024 * 1024 * 100];
        long readCount;
        long total = 0;
        long startTime = System.currentTimeMillis();

        while ((readCount = bufferedInputStream.read(bytes)) >= 0) {
            total += readCount;
            System.out.println("传输中。。。");
            bufferOutPutStream.write(bytes);
        }
        System.out.println("发送总字节数 " + total + ",耗时：" + (System.currentTimeMillis() - startTime));
        bufferOutPutStream.close();
        socket.close();
        bufferedInputStream.close();
    }
}
