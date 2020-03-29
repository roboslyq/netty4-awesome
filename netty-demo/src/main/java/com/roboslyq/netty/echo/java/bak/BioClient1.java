/**
 * Copyright (C), 2015-2020
 * FileName: BioClient1
 * Author:   luo.yongqian
 * Date:     2020/3/26 12:14
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/26 12:14      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.bak;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 〈〉
 *
 * @author luo.yongqian
 * @date 2020/3/26
 * @since 1.0.0
 */
public class BioClient1 {
    private static java.nio.charset.Charset charset = java.nio.charset.Charset.forName("UTF-8");

    public static void main(String[] args) throws Exception {
        test1();
//        test3();
        return;
    }

    private static void test1() throws IOException, InterruptedException {
        Socket socket =  new Socket("127.0.0.1", 8081);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedReader reader2 = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            reader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new Thread(new WorkThread(reader2)).start();
            String lineString = "";
            while (!(lineString = reader.readLine()).equals("exit")) {
                writer.write(lineString + "\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void test3() throws IOException, InterruptedException {
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedReader reader2 = null;
        try {
            socket = new Socket("127.0.0.1", 8081);
            reader = new BufferedReader(new InputStreamReader(System.in));
            reader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new Thread(new WorkThread(reader)).start();
            String lineString = "";
            while (!(lineString = reader.readLine()).equals("exit")) {
                writer.write(lineString + "\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void test2() throws IOException, InterruptedException {
        Socket s = new Socket("localhost", 8081);
        OutputStream out = s.getOutputStream();
        // 阻塞，写完成
        out.write("开始连接..".getBytes(charset));

        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            System.out.println("请输入：");
            String msg = scanner.nextLine();
            System.out.println("您输入的值为: " + msg);
            // 阻塞，写完成
            out.write(msg.getBytes(charset));
            out.flush();
            if (msg.equals("exit")) {
                break;
            }
            System.out.println("发送完成");
            TimeUnit.SECONDS.sleep(1);
        }
        scanner.close();
        s.close();
    }

    /**
     *
     */
    static class WorkThread implements Runnable {

        BufferedReader reader;

        public WorkThread(BufferedReader reader) {
            super();
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                String lineString = "";
                while ((lineString = reader.readLine()) != null) {
                    System.out.println(lineString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
