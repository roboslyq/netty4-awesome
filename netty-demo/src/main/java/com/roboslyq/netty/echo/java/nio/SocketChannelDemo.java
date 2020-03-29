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
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/3/28
 * @since 1.0.0
 */
public class SocketChannelDemo {

    public static void main(String[] args) {
        SocketChannelDemo ssd = new SocketChannelDemo();
        ssd.clientStart();
    }

    /**
     *
     */
    public void clientStart(){
        ByteBuffer buffer = ByteBuffer.allocate(Constants.BUF_SIZE);
        SocketChannel socketChannel = null;
        try
        {
            // 通过工厂模式，创建SocketChannel实例：SocketChannelImpl
            socketChannel = SocketChannel.open();
            // 设置非阻塞模式
            socketChannel.configureBlocking(false);
            // 与服务端进行连接
            socketChannel.connect(new InetSocketAddress(Constants.HOST,Constants.PORT));
            // 如果连接成功
            if(socketChannel.finishConnect())
            {
                int i=0;
                while(true)
                {
                    TimeUnit.SECONDS.sleep(1);
                    // 将要发送的内容
                    String info = "I'm "+i+++"-th information from clientStart";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while(buffer.hasRemaining()){
                        System.out.println(buffer);
                        socketChannel.write(buffer);
                    }
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(socketChannel!=null){
                    socketChannel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}