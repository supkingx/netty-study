package com.supkingx.netty.dubborpc.provider;

import com.supkingx.netty.dubborpc.publicinterface.HelloService;

/**
 * @description:
 * @Author: wangchao
 * @Date: 2022/1/6
 */
public class HelloServiceImpl implements HelloService {
    // 验证每次调用的是不是用一个 service
    int count = 0;

    // 当有消费方调用该方法时，就返回一个结果
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        // 根据 msg 返回不同的结果
        if (msg != null) {
            return "你好客户端，我已经收到你的消息[" + msg + "] 第" + (++count) + "次";
        } else {
            return "你好客户端，我已经收到你的消息 ";
        }
    }
}
