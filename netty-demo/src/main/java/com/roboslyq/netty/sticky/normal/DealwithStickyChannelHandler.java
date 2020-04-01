/**
 * Copyright (C), 2015-2020
 * FileName: DealwithStickyServerChannelHandler
 * Author:   luo.yongqian
 * Date:     2020/4/1 22:40
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/1 22:40      1.0.0               创建
 */
package com.roboslyq.netty.sticky.normal;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/1
 * @since 1.0.0
 */
public class DealwithStickyChannelHandler extends SimpleChannelInboundHandler {
    /**
     * 注意此处末尾的换行符不能少,否则服务端可能无法接收.
     */
    byte[] req = "hello world, i am a developer. \n".getBytes();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String body = new String(bytes);
        System.out.println("body: " + body);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 1000; i++) {
            System.out.println("客户端第 " +i+ " 次发送");
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }
}