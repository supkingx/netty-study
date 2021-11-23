package com.supkingx.nio.iocopy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 传统的IO服务器
 * @Author: wangchao
 * @Date: 2021/11/23
 */
public class OldIoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("test01.txt"));
                byte[] bytes = new byte[1024 * 1024 * 100];
                int len;
                while ((len = bufferedInputStream.read(bytes)) != -1) {
                    System.out.println("输出");
                    bufferedOutputStream.write(bytes, 0, len);
                }
                bufferedInputStream.close();
                bufferedOutputStream.close();
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
