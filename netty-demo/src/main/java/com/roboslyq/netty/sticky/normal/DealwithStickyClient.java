/**
 * Copyright (C), 2015-2020
 * FileName: DealwithStickyServer
 * Author:   luo.yongqian
 * Date:     2020/4/1 22:31
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/1 22:31      1.0.0               创建
 */
package com.roboslyq.netty.sticky.normal;

import com.roboslyq.netty.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/1
 * @since 1.0.0
 */
public class DealwithStickyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new DealwithStickyHandlerInitializer());
        InetSocketAddress inetSocketAddress = new InetSocketAddress(Constants.HOST, Constants.PORT);

        ChannelFuture future =bootstrap.connect(inetSocketAddress).sync();

        future.channel().closeFuture().sync();

    }

}