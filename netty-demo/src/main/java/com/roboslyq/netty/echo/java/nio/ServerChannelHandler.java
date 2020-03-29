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

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/3/29
 * @since 1.0.0
 */
public class ServerChannelHandler {
    /**
     * 服务端处理客户端连接事件：连接之后最终的目的是注册可读事件，等待客户端发送数据。
     *
     * @param key
     */
    public void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        //与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着FIleChannel与Selector不能一起使用。
        sc.configureBlocking(false);
        //当连接建立起来之后，就需要重新在Selector上注册新的事件监听(READ事件): 必须指定Buffer,否则read时可能为报NullPointerException
        sc.register(key.selector(),SelectionKey.OP_READ,ByteBuffer.allocateDirect(Constants.BUF_SIZE));

        //将此对应的channel设置为准备接受其他客户端请求
        key.interestOps(SelectionKey.OP_ACCEPT);

        System.out.println("Server is listening from client :" + sc.getRemoteAddress());
        sc.write(Charset.forName("UTF-8").encode("Please input your name."));
    }

    public void handleWrite(SelectionKey key) throws IOException {
        System.out.println("start to write message: ");
        SocketChannel sc = (SocketChannel) key.channel();
        // 将要发送的内容
        ByteBuffer buffer = ByteBuffer.allocate(Constants.BUF_SIZE);
        String info = "response from server: successed!!";
        buffer.clear();
        buffer.put(info.getBytes());
        buffer.flip();
        while(buffer.hasRemaining()){
            sc.write(buffer);
        }
        System.out.println("往客户端写完成！");
        //将感兴趣事件修改为 可读
//        key.interestOps(SelectionKey.OP_READ);
        sc.register(key.selector(),SelectionKey.OP_READ,ByteBuffer.allocateDirect(Constants.BUF_SIZE));
    }

    /**
     * 可读事件处理
     *
     * @param key
     */
    public void handleRead(SelectionKey key) throws IOException {
        System.out.println("当前Key可读事件：");
        //注意：这里的Channel类型不在是ServerSocketChannel，而是SocketChannel
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        if(buf == null){
            return;
        }
        int bytesRead = sc.read(buf);
        while (bytesRead > 0) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        //将此对应的channel设置为准备下一次接受数据
        key.interestOps(SelectionKey.OP_READ);
        sc.register(key.selector(),SelectionKey.OP_READ,ByteBuffer.allocateDirect(Constants.BUF_SIZE));
        sc.write(Charset.forName("UTF-8").encode("处理成功！"));
    }

    /**
     * 客户端与服务端连接成功之后处理：   TODO 可以使用多线程
     * @param selectionKey
     */
    public void  connectable(SelectionKey selectionKey){
        System.out.println("客户端开始connectable处理：");
        try{
            //获取对应的客户端 channel
            SocketChannel clientChannel= (SocketChannel)selectionKey.channel();

            //如果正在连接，则完成连接
            if(clientChannel.isConnectionPending()){
                //当 channel 的 socket 连接上后，返回true
                clientChannel.finishConnect();
            }

            //开始写操作:从控制台输入发送给服务端的信息
            Scanner scanner=new Scanner(System.in);
            String response=scanner.nextLine()+System.getProperty("line.separator");

            int len= clientChannel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));

            //将感兴趣事件修改为 可读: 等待服务端响应
            selectionKey.interestOps(SelectionKey.OP_READ);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}