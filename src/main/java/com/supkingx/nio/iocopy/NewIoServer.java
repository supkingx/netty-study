package com.supkingx.nio.iocopy;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/23
 */
public class NewIoServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(7001));

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("test01.txt"));
        while (true) {
            try {
                int readCount = 0;
                SocketChannel socketChannel = serverSocketChannel.accept();
                while ((readCount = socketChannel.read(byteBuffer)) != -1) {
                    bufferedOutputStream.write(byteBuffer.array(), 0, readCount);
                    byteBuffer.rewind(); // 倒带 position = 0, mark 作废
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
