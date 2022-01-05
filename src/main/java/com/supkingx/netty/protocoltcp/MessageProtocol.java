package com.supkingx.netty.protocoltcp;

/**
 * @description: 协议包
 * @Author: wangchao
 * @Date: 2022/1/5
 */
public class MessageProtocol {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
