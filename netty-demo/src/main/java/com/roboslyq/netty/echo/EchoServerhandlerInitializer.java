/**
 * Copyright (C), 2015-2020
 * FileName: EchoServerhandlerInitializer
 * Author:   luo.yongqian
 * Date:     2020/3/25 0:27
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/25 0:27      1.0.0               创建
 */
package com.roboslyq.netty.echo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 *
 * 〈〉
 * @author luo.yongqian
 * @date 2020/3/25
 * @since 1.0.0
 */
public class EchoServerhandlerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new EchoServerHandler());
    }
}