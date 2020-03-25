/**
 * Copyright (C), 2015-2020
 * FileName: EchoHttpServer
 * Author:   luo.yongqian
 * Date:     2020/3/24 22:56
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/24 22:56      1.0.0               创建
 */
package com.roboslyq.netty.echo.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * 〈服务端〉
 * @author luo.yongqian
 * @date 2020/3/24
 * @since 1.0.0
 */
public class EchoSocketServer {
    public static void main(String[] args) throws InterruptedException {
        //事件循环组(死循环)
        //boss线程池，处理连接事务。将具体的处理工作交给workGroup
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //worker线程池，处理具体的数据
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //Netty对服务的启动进行了统一包装，十分方便快捷
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    //设置工作线程组
                    .group(bossGroup,workGroup)
                    //设置通讯类型，此处为Nio的Server端
                    .channel(NioServerSocketChannel.class)
                    //设置bossGroup级的相关Handler
                    .handler(new LoggingHandler())
                    //设置workGroup相关Handler
                    .childHandler(
                            // Handler初始化器
                            new EchoSocketServerhandlerInitializer()
                    );
            //绑定一个端口并且同步，生成一个ChannelFuture对象
            ChannelFuture f=  bootstrap.bind(8081).sync();
            // Wait until the server socket is closed.
            //对关闭的监听
            f.channel().closeFuture().sync();
        }finally {
            //循环组优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}