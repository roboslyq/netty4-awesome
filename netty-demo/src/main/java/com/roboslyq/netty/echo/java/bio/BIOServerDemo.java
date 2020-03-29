/**
 * Copyright (C), 2015-2020
 * FileName: BIOServerDemo
 * Author:   luo.yongqian
 * Date:     2020/3/28 0:30
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/28 0:30      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * 〈〉
 * @author luo.yongqian
 * @date 2020/3/28
 * @since 1.0.0
 */
public class BIOServerDemo {
    public static Executor executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8081);
        for(;;){
            Socket request = serverSocket.accept();
            rev(request);
        }

    }

    public static void rev(Socket socket) throws Exception{
        executor.execute(()->{
            BufferedReader reader = null;
            BufferedWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String data;
                while ((data = reader.readLine()) != null){
                    System.out.println("接收到客户端请求： " + data);
                    writer.write("已经成功处理：" + data +"\n");
                    writer.flush();
                }
            } catch (IOException e) {
                System.out.println("客户端关闭");
            }

        });
    }
}