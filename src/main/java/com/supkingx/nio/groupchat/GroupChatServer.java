package com.supkingx.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2021/11/22
 */
public class GroupChatServer {
    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    // 构造器
    // 初始化工作
    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // 获取 ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 配置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将该 listenChannel 注册到 selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 监听
    public void listen() {
        System.out.println("监听线程：" + Thread.currentThread().getName());
        try {
            while (true) {
//                int count = selector.select(2000);
                int count = selector.select();
                if (count > 0) { // 有事件处理
                    // 遍历得到的 SelectionKey 集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        // 取出 selectionKey
                        SelectionKey key = iterator.next();

                        // 监听到 accept
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            // 将该 socketChannel 注册到 Selector
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            // 提示
                            System.out.println(socketChannel.getRemoteAddress() + " 上线 ");
                        }
                        if (key.isReadable()) { //通道发送read事件，即通道可读
                            // 处理读
                            readData(key);
                        }

                        // 当前的key删除，防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待。。。。。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    // 读取客户端信息
    private void readData(SelectionKey key) {

        SocketChannel channel = null;
        try {
            // 取到关联的 channel
            channel = (SocketChannel) key.channel();
            // 创建 buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            // 根据count做处理
            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from 客户端：" + msg);

                // 向其他客户端转发消息(排除自己)
                sendInfoToOtherClients(msg, channel);
            }
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    // 转发消息给其他客户
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程。当前线程：" + Thread.currentThread().getName());
        // 遍历 所有注册到 selector 上的 SocketChannel，并删除 self
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();

            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将 msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                // 将 buffer 数据写入到 通道
                System.out.println("转发给" + dest.getRemoteAddress().toString().substring(1) + ",内容：【" + msg + "]");
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
