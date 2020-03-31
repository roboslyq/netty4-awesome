/**
 * Copyright (C), 2015-2020
 * FileName: AsyncServerSocketChannelDemo
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
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * 〈〉
 *
 * @author roboslyq
 * @date 2020/3/30
 * @since 1.0.0
 */
public class AsyncServerSocketChannelDemo {


    public static void main(String[] args) {
        try {
            AsyncServerSocketChannelDemo asyncServer = new AsyncServerSocketChannelDemo();
            asyncServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws Exception
     */
    public void start() throws Exception {
        //  与普通的TCP一致
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(Constants.HOST, Constants.PORT);
        serverSocketChannel.bind(inetSocketAddress);

        //创建监听对象，监听异步，为普通的Futrue对象
        Future<AsynchronousSocketChannel> accept;

        while (true) {
            // accept()不会阻塞。
            accept = serverSocketChannel.accept();

            System.out.println("=================");
            System.out.println("服务器等待连接...");

            // get()方法将阻塞。
            AsynchronousSocketChannel socketChannel = accept.get();

            System.out.println("服务器接受连接");
            System.out.println("服务器与" + socketChannel.getRemoteAddress() + "建立连接");

            ByteBuffer buffer = ByteBuffer.wrap("roboslyq".getBytes());

            //不会阻塞
            Future<Integer> write = socketChannel.write(buffer);
            //阻塞
            while (!write.isDone()) {
                Thread.sleep(10);
            }

            System.out.println("服务器发送数据完毕.");
            socketChannel.close();
        }
    }
}