/**
 * Copyright (C), 2015-2020
 * FileName: EchoHttpServerHandlerInitializer
 * Author:   luo.yongqian
 * Date:     2020/3/25 0:27
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/25 0:27      1.0.0               创建
 */
package com.roboslyq.netty.echo.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 〈Channel初始化器，每当有一个新的客户端发建立起连接时，均会调用此类的initChannel方法，当前Channel相关的初始化工作。
 *
 * @author luo.yongqian
 * @date 2020/3/25
 * @since 1.0.0
 */
public class EchoHttpServerHandlerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //每次都是通过new创建各组件实现，保证不同channel的具体handler实现不同
        ch.pipeline()
                .addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(1024 * 10 * 1024))
                .addLast(new EchoHttpServerHandler())
                //解压
                .addLast(new HttpContentDecompressor());

    }
}