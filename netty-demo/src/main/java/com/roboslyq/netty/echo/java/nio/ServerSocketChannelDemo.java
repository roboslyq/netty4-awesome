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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈Channel相当于Connection抽象，因此是由此类完成端口绑定等相关操作
 *
 * 〉
 *
 * @author roboslyq
 * @date 2020/3/28
 * @since 1.0.0
 */
public class ServerSocketChannelDemo {

    //存储SelectionKey的队列
    public static ConcurrentHashMap<SelectionKey,String> writeQueue = new ConcurrentHashMap();
    static Selector  selector = null;
    static ServerSocketChannel ssc =null;
    private ServerChannelHandler handler = new ServerChannelHandler();

    public static void main(String[] args) {
        ServerSocketChannelDemo ssc = new ServerSocketChannelDemo();
        try {
            ssc.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //添加SelectionKey到队列
    public static void addWriteQueue(SelectionKey key){
        synchronized (writeQueue) {
            writeQueue.put(key,"");
            //唤醒主线程
            selector.wakeup();
        }
    }
    /**
     * 启动服务
     * @throws Exception
     */
    public void startServer() throws Exception {
        //

        //1、创建Channel实例
        ssc = ServerSocketChannel.open();


        //2、绑定IP和PORT
        InetAddress inetAddress = InetAddress.getByName(Constants.HOST);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress,Constants.PORT);
        ssc.socket().bind(inetSocketAddress);

        //必须异步，否则启动报错
        ssc.configureBlocking(false);

        //3、创建Selector
        selector = Selector.open();

        /*
         * 4.注册事件类型
         *      sel:通道选择器
         *      ops:事件类型 ==>SelectionKey:包装类，包含事件类型和通道本身。四个常量类型表示四种事件类型
         *      SelectionKey.OP_ACCEPT 获取报文      SelectionKey.OP_CONNECT 连接
         *      SelectionKey.OP_READ 读           SelectionKey.OP_WRITE 写
         */
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("server started on " + Constants.PORT);

        int count = 0;
        //5、循环监听事件（多线程环境可持续读问题，很容易引起CPU空转(100%)）
        for (;;) {
            // 检查是否已经有KEY准备好I/O 相关操作。
            if(selector.select(Constants.TIME_OUT) == 0){
                continue;
            }
            //6、获取对应的keys    (此处不阻塞!!!)
           Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            //7、遍列事件key
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                //8、各种事件处理
                keyIterator.remove();
                System.out.println("------------->" + count++);
                // 处理具体的Key事件
                doSelectionkeyHandle(key);
            };
        }

    }

    private void doSelectionkeyHandle( SelectionKey key) {
        //处理连接事件，可以使用一个新线程
        if (key.isAcceptable()) {
            try {
                handler.handleAccept(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //处理可读事件，可以使用一个新线程
        if (key.isReadable()) {
            try {
                if(writeQueue.containsKey(key)){
                    System.out.println("已经包含Key,不需要重复处理------------->");
                    return;
                }else{
                    addWriteQueue(key);
                    handler.handleRead(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //可以写事件，可以使用一个新线程
        if (key.isWritable() && key.isValid()) {
            try {
                handler.handleWrite(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //是否可以连接
        if (key.isConnectable()) {
            System.out.println("isConnectable = true");
        }
    }
}
