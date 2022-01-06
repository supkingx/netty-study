package com.supkingx.netty.dubborpc.customer;

import com.supkingx.netty.dubborpc.netty.NettyClient;
import com.supkingx.netty.dubborpc.publicinterface.HelloService;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/6
 */
public class ClientBootstrap {

    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        // 创建一个消费者
        NettyClient customer = new NettyClient();
        // 创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        for (; ; ) {
            // 验证每次调用的是不是用一个 service
            // 通过代理对象调用服务提供者的方法
            String hello = service.hello("你好 dubbo~");
            System.out.println("调用结果 res=" + hello);
            Thread.sleep(10 * 1000);
        }
    }
}
