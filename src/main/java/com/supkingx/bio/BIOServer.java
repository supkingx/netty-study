package com.supkingx.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/20
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 线程池机制
        //1. 创建一个线程池
        //2. 如果有客户端连接，就创建一个线程，与之通讯(单独写一个方法)
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            System.out.println("等待连接");
            Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });

        }
    }

    // 编写一个handler方法，和客户端通信
    public static void handler(Socket socket) {
        try {
            System.out.println("线程信息 id=" + Thread.currentThread().getId() + ",线程名字=" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            // 通过 socket 获取输入流
            System.out.println("等待客户端输入");
            InputStream inputStream = socket.getInputStream();

            // 循环的读取客户端发送的数据
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    // 输出客户端发送的数据
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
