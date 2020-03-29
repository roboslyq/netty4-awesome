/**
 * Copyright (C), 2015-2020
 * FileName: BioServer
 * Author:   luo.yongqian
 * Date:     2020/3/26 8:52
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/26 8:52      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.bak;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO 服务端
 */
public class BioServer {

    public static void main(String[] args) {
        BioServer bioServer = new BioServer();
        bioServer.startAction();
    }

    public void startAction(){
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(8081);  //端口号
            System.out.println("服务端服务启动监听：");
            //通过死循环开启长连接，开启线程去处理消息
            while(true){
                Socket socket=serverSocket.accept();
                new Thread(new ServerWorker(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket!=null) {
                    serverSocket.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    class ServerWorker implements Runnable{

        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;

        public ServerWorker(Socket socket) {
            super();
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//读取客户端消息  
                writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//向客户端写消息
                String lineString="";

                while(!(lineString=reader.readLine()).equals("bye")){
                    System.out.println("收到来自客户端的发送的消息是：" + lineString);
//                    writer.write("服务器返回："+lineString+"\n");
//                    writer.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader!=null) {
                        reader.close();
                    }
                    if (writer!=null) {
                        writer.close();
                    }
                    if (socket!=null) {
                        socket.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

    }
}