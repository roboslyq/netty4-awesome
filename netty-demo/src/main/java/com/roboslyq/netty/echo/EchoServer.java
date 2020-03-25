/**
 * Copyright (C), 2015-2020
 * FileName: EchoServer
 * Author:   luo.yongqian
 * Date:     2020/3/24 22:56
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/24 22:56      1.0.0               创建
 */
package com.roboslyq.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * 〈〉
 * @author luo.yongqian
 * @date 2020/3/24
 * @since 1.0.0
 */
public class EchoServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler())
                .childHandler(new EchoServerhandlerInitializer());
       ChannelFuture f=  bootstrap.bind(8081).sync();
        // Wait until the server socket is closed.
        f.channel().closeFuture().sync();
    }

}