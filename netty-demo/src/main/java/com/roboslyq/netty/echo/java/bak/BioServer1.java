/**
 * Copyright (C), 2015-2020
 * FileName: BioServer1
 * Author:   luo.yongqian
 * Date:     2020/3/26 12:13
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/26 12:13      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.bak;

import io.netty.util.CharsetUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 〈基于TCP协议的Socket通信，实现用户登录，服务端〉
 *
 * @author luo.yongqian
 * @date 2020/3/26
 * @since 1.0.0
 */
public class BioServer1 {

    public static void main(String[] args) throws Exception {
        //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("服务器启动成功");
        while (true) {
            // 2、调用accept()方法开始监听，等待客户端的连接(阻塞）
            Socket request = serverSocket.accept();
            System.out.println("收到新连接 : " + request.toString());
            try {
                //3、获取输入流，并读取客户端信息接收数据、打印
                InputStream inputStream = request.getInputStream();
                OutputStream outputStream = request.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CharsetUtil.UTF_8));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, CharsetUtil.UTF_8));
                String msg;
                // 没有数据，阻塞
                while ((msg = reader.readLine()) != null) {
                    System.out.println("收到数据"+msg+",来自：" + request.toString());
                    writer.write("服务器已经收到你的请求：" + msg + "\n");
                    writer.flush();
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    request.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
