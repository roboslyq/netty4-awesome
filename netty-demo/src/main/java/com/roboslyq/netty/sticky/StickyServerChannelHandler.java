/**
 * Copyright (C), 2015-2020
 * FileName: StickyServerChannelHandler
 * Author:   luo.yongqian
 * Date:     2020/4/1 22:40
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/1 22:40      1.0.0               创建
 */
package com.roboslyq.netty.sticky;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/1
 * @since 1.0.0
 */
public class StickyServerChannelHandler extends SimpleChannelInboundHandler {

    int i = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("第" + i++ + "收到客户请求: " + stickyTest(msg));
//        System.out.println("第" + i++ + "收到客户请求: " + stringDecoderTest(msg));


    }

    /**
     * 未配置解码器测试，存在粘包和拆包情况
     * @param msg
     */
    public String stickyTest(Object msg){

        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
       return new String(bytes);
    }
    /**
     * 未配置解码器测试，存在粘包和拆包情况
     * @param msg
     */
    public String stringDecoderTest(Object msg){

        return (String)msg;
    }
}