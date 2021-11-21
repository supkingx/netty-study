package com.supkingx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @description: Scattering：将数据写入到 buffer 时，可以采用 buffer 数组，依次写入 [分散]
 * Gathering：从 buffer 读取数据时，可以采用 buffer 数组，依次读
 * @Author: wangchao
 * @Date: 2021/11/21
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        // 使用 ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7070);
        // 绑定 端口
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建 buffer 数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接（telnet）
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;

        while (true) {
            int byteRead = 0;
            // 数据小于8则进行输出
            while (byteRead < messageLength) {
                long l = socketChannel.read(byteBuffers);
                // 累计读取的字节数
                byteRead += l;
                System.out.println("byteRead=" + byteRead);
                // 输出
                Arrays.stream(byteBuffers).map(buffer -> "postion=" + buffer.position() + ",limit=" + buffer.limit()).forEach(System.out::println);
            }

            // 将所有的 buffer 进行 flip
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            // byteBuffers中长度超过messageLength 后将buffer中的数据全部输出到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            // 将所有的 buffer 进行 clear
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead = " + byteRead + ", byteWrite = " + byteWrite + ", messagelength = " + messageLength);
        }
    }
}
