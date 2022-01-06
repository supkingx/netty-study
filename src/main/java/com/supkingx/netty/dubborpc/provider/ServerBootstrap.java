package com.supkingx.netty.dubborpc.provider;

import com.supkingx.netty.dubborpc.netty.NettyServer;

/**
 * @description: 会启用一个服务的提供者，就是NettyServer
 * @Author: wangchao
 * @Date: 2022/1/6
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",8989);
    }
}
