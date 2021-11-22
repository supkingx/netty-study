package com.supkingx.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/22
 */
public class GroupChatClient {
    private final static String HOST = "127.0.0.1";
    private final static int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    // 构造器，完成初始化工作
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        // 连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 得到 username
//        username = socketChannel.getLocalAddress().toString().substring(1);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    // 向服务器发送消息
    public void sendInfo(String info) {
        info = username + "说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取从服务器端回复的消息
    public void readInfo() {
        try {
//            int readChannels = selector.select(2000);
            int readChannels = selector.select();
            
            if (readChannels > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        // 得到相关的通道
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        // 得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // 读取
                        socketChannel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg);
                    }
                }
                // 删除当前 SelectionKey 防止重复操作
                iterator.remove();
            } else {
//                System.out.println("没有可用的通道");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // 启动客户端
        GroupChatClient groupChatClient = new GroupChatClient();

        // 启动一个线程,每隔三秒，从服务器端读取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    groupChatClient.readInfo();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
// 测试push权限
        // 发送数据给服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            groupChatClient.sendInfo(s);
        }
    }
}
