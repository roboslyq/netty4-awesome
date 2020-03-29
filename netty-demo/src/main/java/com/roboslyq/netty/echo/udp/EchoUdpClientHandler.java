/**
 * Copyright (C), 2015-2020
 * FileName: EchoUdpClientHandler
 * Author:   luo.yongqian
 * Date:     2020/3/26 0:06
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/26 0:06      1.0.0               创建
 */
package com.roboslyq.netty.echo.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * 〈〉
 *
 * @author luo.yongqian
 * @date 2020/3/26
 * @since 1.0.0
 */
public class EchoUdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable
            cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String response = msg.content().toString(CharsetUtil.UTF_8);
        if (response.startsWith("谚语查询结果：")) {
            System.out.println(response);
            ctx.close();
        }
    }

}