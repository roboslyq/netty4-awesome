/**
 * Copyright (C), 2015-2020
 * FileName: ServerSocketChannelDemo
 * Author:   luo.yongqian
 * Date:     2020/3/28 23:12
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/28 23:12      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.nio;

import com.roboslyq.netty.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * 〈Channel相当于Connection抽象，因此是由此类完成端口绑定等相关操作〉
 *
 * @author roboslyq
 * @date 2020/3/28
 * @since 1.0.0
 */
public class ServerSocketChannelDemo {

    public static void main(String[] args) {
        ServerSocketChannelDemo ssc = new ServerSocketChannelDemo();
        try {
            ssc.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务
     * @throws Exception
     */
    public void startServer() throws Exception {
        //
        ServerChannelHandler handler = new ServerChannelHandler();

        //1、创建Channel实例
        ServerSocketChannel ssc = ServerSocketChannel.open();


        //2、绑定IP和PORT
        InetAddress inetAddress = InetAddress.getByName(Constants.HOST);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress,Constants.PORT);
        ssc.socket().bind(inetSocketAddress);

        //必须异步，否则启动报错
        ssc.configureBlocking(false);

        //3、创建Selector
        Selector selector = Selector.open();

        //4、把ServerSocketChannel注册到selector中,并设置监听事件《监听客户端发起的连接》
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        //5、循环监听事件
        for (;;) {
            // 检查是否已经有KEY准备好I/O 相关操作。
            if(selector.select(Constants.TIME_OUT) == 0){
                continue;
            }else {
                System.out.println("收到连接事件，开始处理：");
            }

            //6、获取对应的keys    (此处不阻塞!!!)
            Set<SelectionKey> keys = selector.selectedKeys();

            //7、遍列事件key
            keys.forEach(key -> {
                //8、各种事件处理

                //将当前key从keys中删除
                keys.remove(key);

                //处理连接事件，可以使用一个新线程
                if (key.isAcceptable()) {
                    try {
                        handler.handleAccept(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //处理可读事件，可以使用一个新线程
                if (key.isReadable()) {
                    try {
                        handler.handleRead(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //可以写事件，可以使用一个新线程
                if (key.isWritable() && key.isValid()) {
                    try {
                        handler.handleWrite(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //是否可以连接
                if (key.isConnectable()) {
                    System.out.println("isConnectable = true");
                }
            });
        }

    }
}
