/**
 * Copyright (C), 2015-2020
 * FileName: UdpClient
 * Author:   luo.yongqian
 * Date:     2020/3/28 10:10
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/28 10:10      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * 〈〉
 * @author luo.yongqian
 * @date 2020/3/28
 * @since 1.0.0
 */
public class UdpClient
{    private static String netAddress = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        //1、客户端启动<不需要绑定任务IP,IP在数据包中指定 >
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(netAddress);

        while (true){
            byte[] buffer =new byte[1024];
            //2、需要发送的内容
            byte[] sendMessage = readerStr().getBytes();
            InetAddress inetAddress = InetAddress.getByName(netAddress);
            /*
             * 3、构建发送数据包：
             *   (1) 消息原文(buffer结构)
             *   (2) 消息长度
             *   (3) 服务器地址
             *   (4) 服务器IP
             */
            DatagramPacket packet  = new DatagramPacket(sendMessage,sendMessage.length,inetAddress,8081);
            //发送数据
            datagramSocket.send(packet);


            byte[] revByteBuffer = new byte[1024];
            DatagramPacket recePacket = new DatagramPacket(revByteBuffer, revByteBuffer.length);
            datagramSocket.receive(recePacket);
            String receStr = new String(recePacket.getData(), 0 , recePacket.getLength());
            System.out.println("收到服务端响应： "+receStr);
        }
    }


    public static String readerStr(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String message = "";
        while (true){
            try {
                if((message = reader.readLine()) != null){
                    return message;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}