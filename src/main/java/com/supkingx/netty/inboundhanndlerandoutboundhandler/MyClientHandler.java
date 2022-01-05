package com.supkingx.netty.inboundhanndlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @description: 业务处理
 * @Author: wangchao
 * @Date: 2022/1/3
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器的ip=" + ctx.channel().remoteAddress());
        System.out.println("收到服务器消息=" + msg);
    }

    // 重写 channelActive 发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        ctx.writeAndFlush(123456L); // 发送的是一个 Long

        // 分析
        // 1、 adadadadadadadad 16个字节
        // 2、该处理器的前一个 handler 是 MyLongToByteEncoder
        // 3、MyLongToByteEncoder 父类 MessageToByteEncoder
        // 4、父类 MessageToByteEncoder 有个 write 方法(判断当前msg 是不是应该处理的类型，如果是就处理，如果不是就跳过encoder)
        /**
         *  @Override
         *     public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
         *         ByteBuf buf = null;
         *         try {
         *             if (acceptOutboundMessage(msg)) {  // 判断当前msg 是不是应该处理的类型，如果是就处理，如果不是就跳过encoder
         *                 @SuppressWarnings("unchecked")
         *                 I cast = (I) msg;
         *                 buf = allocateBuffer(ctx, cast, preferDirect);
         *                 try {
         *                     encode(ctx, cast, buf);
         *                 } finally {
         *                     ReferenceCountUtil.release(cast);
         *                 }
         *
         *                 if (buf.isReadable()) {
         *                     ctx.write(buf, promise);
         *                 } else {
         *                     buf.release();
         *                     ctx.write(Unpooled.EMPTY_BUFFER, promise);
         *                 }
         *                 buf = null;
         *             } else {
         *                 ctx.write(msg, promise);
         *             }
         *         } catch (EncoderException e) {
         *             throw e;
         *         } catch (Throwable e) {
         *             throw new EncoderException(e);
         *         } finally {
         *             if (buf != null) {
         *                 buf.release();
         *             }
         *         }
         *     }
         */
        // 5、因此我们编写 encoder 时，要注意传入的数据类型和处理的数据类型要一致
//        ctx.writeAndFlush(Unpooled.copiedBuffer("adadadadadadadad", CharsetUtil.UTF_8));
    }
}
