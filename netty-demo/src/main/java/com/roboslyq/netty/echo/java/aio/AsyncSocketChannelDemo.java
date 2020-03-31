/**
 * Copyright (C), 2015-2020
 * FileName: AsyncSocketChannelDemo
 * Author:   luo.yongqian
 * Date:     2020/3/30 23:38
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/30 23:38      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.aio;

import com.roboslyq.netty.Constants;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * 〈〉
 *
 * @author roboslyq
 * @date 2020/3/30
 * @since 1.0.0
 */
public class AsyncSocketChannelDemo {
    public static void main(String[] args) {
        AsyncSocketChannelDemo asyncChannel = new AsyncSocketChannelDemo();
        asyncChannel.connect();
    }

    /**
     * 连接客户端
     */
    private void connect() {
        AsynchronousSocketChannel socketChannel = null;
        try {
            // 创建Channel
            socketChannel = AsynchronousSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(Constants.HOST, Constants.PORT);

            //绑定端口，不阻塞<返回Future>
            Future<Void> connect = socketChannel.connect(inetSocketAddress);

            while (!connect.isDone()) {
                Thread.sleep(10);
            }

            System.out.println("建立连接" + socketChannel.getRemoteAddress());

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //
            Future<Integer> read = socketChannel.read(buffer);

            while (!read.isDone()) {
                Thread.sleep(10);
            }

            System.out.println("接收服务器数据:" + new String(buffer.array(), 0, read.get()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}