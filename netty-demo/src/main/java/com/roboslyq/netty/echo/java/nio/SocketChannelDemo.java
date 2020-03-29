/**
 * Copyright (C), 2015-2020
 * FileName: SocketChannelDemo
 * Author:   luo.yongqian
 * Date:     2020/3/28 23:59
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/28 23:59      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.nio;

import com.roboslyq.netty.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 〈〉
 *
 * @author roboslyq
 * @date 2020/3/28
 * @since 1.0.0
 */
public class SocketChannelDemo {
    Selector selector = null;
    private Charset charset = Charset.forName("UTF-8");
    ByteBuffer writeBuffer = ByteBuffer.allocate(Constants.BUF_SIZE);
    ByteBuffer readBuffer = ByteBuffer.allocate(Constants.BUF_SIZE);
    Executor executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        SocketChannelDemo ssd = new SocketChannelDemo();
        ssd.clientStart();
    }

    /**
     *
     */
    public void clientStart() throws Exception {
        SocketChannel socketChannel = null;
        selector = Selector.open();
        try {
            // 1、通过工厂模式，创建SocketChannel实例：SocketChannelImpl
            socketChannel = SocketChannel.open(new InetSocketAddress(Constants.HOST, Constants.PORT));

            // 2、与服务端进行连接
//            socketChannel.connect(new InetSocketAddress(Constants.HOST, Constants.PORT));
//            socketChannel.open();

            // 设置非阻塞模式
            socketChannel.configureBlocking(false);
            // 3、注册连接事件
//            socketChannel.register(selector,SelectionKey.OP_CONNECT);
            socketChannel.register(selector,SelectionKey.OP_READ);


            //开辟一个新线程来读取从服务器端的数据
            executor.execute(new SocketChannelDemo.ClientThread());

            //在主线程中 从键盘读取数据输入到服务器端
            Scanner scan = new Scanner(System.in);

            while(scan.hasNextLine())
            {
                String line = scan.nextLine();

                //不允许发空消息
                if("".equals(line)) {
                    continue;
                }


                //sc既能写也能读，这边是写
                socketChannel.write(charset.encode(line));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                while(true) {
                    int readyChannels = selector.select();
                    if(readyChannels == 0) continue;
                    //可以通过这个方法，知道可用通道的集合
                    Set selectedKeys = selector.selectedKeys();
                    Iterator keyIterator = selectedKeys.iterator();

                    while(keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        dealWithSelectionKey(sk);
                    }
                }
            }
            catch (IOException io)
            {}
        }

        /**
         * 读取SelectKey的数据
         *
         * @param sk
         * @throws IOException
         */
        private void dealWithSelectionKey(SelectionKey sk) throws IOException {
            if(sk.isReadable())
            {
                //使用 NIO 读取 Channel中的数据，这个和全局变量sc是一样的，因为只注册了一个SocketChannel
                //sc既能写也能读，这边是读
                SocketChannel sc = (SocketChannel)sk.channel();

                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";
                while(sc.read(buff) > 0)
                {
                    buff.flip();
                    content += Charset.forName("UTF-8").decode(buff);
                }

                System.out.println(content);
                sk.interestOps(SelectionKey.OP_READ);
            }
        }
    }
}