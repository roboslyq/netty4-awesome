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
package com.roboslyq.netty.echo.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 *
 * 〈Socket相关Handler，注意泛型类型。此处泛型与EchoServerhandlerInitializer中Pipeline的配置息息相关〉
 * @author luo.yongqian
 * @date 2020/3/25
 * @since 1.0.0
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到客户端"+ctx.channel().remoteAddress()+"消息: "+msg);
        //响应给客户端
        ctx.channel().writeAndFlush("from Server"+ UUID.randomUUID());
    }
}