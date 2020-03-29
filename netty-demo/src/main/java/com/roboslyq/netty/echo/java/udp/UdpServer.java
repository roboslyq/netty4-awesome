/**
 * Copyright (C), 2015-2020
 * FileName: UdpServer
 * Author:   luo.yongqian
 * Date:     2020/3/28 9:56
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/28 9:56      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

/**
 *
 * 〈注意： UDP是无连接的，不可靠的传输协议。因此相对于TCP,没有Channel(连接通道)的概念，
 * 直接通过IP:PORT广播消息,当然，也可以指定127.0.0.1等具体的本地IP。
 * 〉
 * @author luo.yongqian
 * @date 2020/3/28
 * @since 1.0.0
 */
public class UdpServer {

    public static void main(String[] args) throws Exception {
        //1、服务器启动:
        InetAddress serverAdress = InetAddress.getByName("127.0.0.1");
//        InetAddress serverAdress = InetAddress.getLocalHost();
//        DatagramSocket datagramSocket = new DatagramSocket(8081);

        DatagramSocket datagramSocket = new DatagramSocket(8081,serverAdress);
        // 2、whilt(true)来循环接收客户端请求：
        while (!datagramSocket.isClosed()){
            //3、UDP的接收内容是 数据包(DatagramPacket),在此处完成数据包的级差
            byte[] revByteBuffer =new byte[1024];
            DatagramPacket packet  = new DatagramPacket(revByteBuffer,revByteBuffer.length);
            //4、阻塞：等待数据包到来
            datagramSocket.receive(packet);
            //5、解析数据包
            String rev = new String(packet.getData());
            System.out.println("接收到数据: " + rev);
            if(rev.equals("exit")){
                datagramSocket.close();
            }
            //6、返回响应给客户端，如果有必要
//            byte[] bt = new byte[packet.getLength()];
            byte[] sendByte = ("已经收到你的请求：" + rev).getBytes();
            System.out.println(packet.getPort());
            System.out.println(packet.getAddress());

//            System.arraycopy(packet.getData(), 0, bt, 0, packet.getLength());
//                packet.setData(sendByte);
            InetAddress client = InetAddress.getByName("127.0.0.1");
            DatagramPacket sendPacket  = new DatagramPacket(sendByte,sendByte.length,packet.getAddress(),packet.getPort());
            datagramSocket.send(sendPacket);
        }
    }

}