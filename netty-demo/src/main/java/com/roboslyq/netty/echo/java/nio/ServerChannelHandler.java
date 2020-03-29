/**
 * Copyright (C), 2015-2020
 * FileName: ClientChannelHandler
 * Author:   luo.yongqian
 * Date:     2020/3/29 10:29
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/29 10:29      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.nio;

import com.roboslyq.netty.Constants;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 〈多线程环境下，怎么处理多次读事件？〉
 *
 * @author roboslyq
 * @date 2020/3/29
 * @since 1.0.0
 */
public class ServerChannelHandler {


    Executor executor = Executors.newFixedThreadPool(10);

    /**
     * 事件1：连接事件
     * 服务端处理客户端连接事件：连接之后最终的目的是注册可读事件，等待客户端发送数据。
     * <p>
     * ==>连接事件不能使用异步，否则会报NullPointException。
     *
     * @param key
     */
    public void handleAccept(SelectionKey key) {
        try {

            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            //与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着FIleChannel与Selector不能一起使用。
            sc.configureBlocking(false);
            //当连接建立起来之后，就需要重新在Selector上注册新的事件监听(READ事件): 必须指定Buffer,否则read时可能为报NullPointerException
            sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(Constants.BUF_SIZE));

            //将此对应的channel设置为准备接受其他客户端请求
            key.interestOps(SelectionKey.OP_ACCEPT);

            System.out.println("Server is listening from client :" + sc.getRemoteAddress());
            sc.write(Charset.forName("UTF-8").encode("Please input your name."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 事件2： 可读事件(客户端发送消息过来)
     * 可读事件处理
     *
     * @param key
     */
    public void handleRead(SelectionKey key) throws IOException {
        //取消读事件的监控《注册表中的信息会删除，无法再次添加》
//        key.cancel();
        executor.execute(() -> {
            try {
                //注意：这里的Channel类型不在是ServerSocketChannel，而是SocketChannel
                SocketChannel sc = (SocketChannel) key.channel();

                ByteBuffer buf = (ByteBuffer) key.attachment();
                int bytesRead = sc.read(buf);
                while (bytesRead > 0) {
                    buf.flip();
                    while (buf.hasRemaining()) {
                        System.out.print((char) buf.get());
                    }
                    //换行
                    System.out.println();
                    buf.clear();
                    bytesRead = sc.read(buf);
                }
                sc.write(Charset.forName("UTF-8").encode("服务器响应: \n 您的请求已经处理成功！"));
                //将感兴趣事件修改为 可读
                key.interestOps(SelectionKey.OP_READ);
                sc.register(key.selector(),SelectionKey.OP_READ,ByteBuffer.allocateDirect(Constants.BUF_SIZE));
                ServerSocketChannelDemo.writeQueue.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 写事件处理：一般情况用不到，正常的写可以在read时，直接通过通道完成写操作。
     *
     * @param key
     * @throws IOException
     */
    public void handleWrite(SelectionKey key) throws IOException {
        //将此对应的channel设置为准备下一次接受数据
        key.interestOps(SelectionKey.OP_READ);

        executor.execute(() -> {
            try {

                System.out.println("start to write message: ");
                SocketChannel sc = (SocketChannel) key.channel();
                // 将要发送的内容
                ByteBuffer buffer = ByteBuffer.allocate(Constants.BUF_SIZE);
                String info = "response from server: successed!!";
                buffer.clear();
                buffer.put(info.getBytes());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    sc.write(buffer);
                }
                System.out.println("往客户端写完成！");
                sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(Constants.BUF_SIZE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}