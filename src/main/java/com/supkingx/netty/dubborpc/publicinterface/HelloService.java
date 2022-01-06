package com.supkingx.netty.dubborpc.publicinterface;

/**
 * @description: 这个接口是服务提供方和服务消费方都需要的
 * @Author: wangchao
 * @Date: 2022/1/6
 */
public interface HelloService {
    
    String hello(String msg);
}
