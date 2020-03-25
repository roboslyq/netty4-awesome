/**
 * Copyright (C), 2015-2020
 * FileName: EchoServerHandler
 * Author:   luo.yongqian
 * Date:     2020/3/25 0:25
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/25 0:25      1.0.0               创建
 */
package com.roboslyq.netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * 〈〉
 * @author luo.yongqian
 * @date 2020/3/25
 * @since 1.0.0
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("msg: " + msg);
    }
}