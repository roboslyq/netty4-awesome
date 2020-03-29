/**
 * Copyright (C), 2015-2020
 * FileName: BioClient
 * Author:   luo.yongqian
 * Date:     2020/3/26 9:01
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/26 9:01      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.bak;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 〈〉
 *
 * @author luo.yongqian
 * @date 2020/3/26
 * @since 1.0.0
 */
public class BioClient {

    public static void main(String[] args) {
        BioClient client = new BioClient();
        client.startAction();
    }

    /**
     *
     */
    class WorkThread implements Runnable {

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
                    System.out.println("服务端响应：" + lineString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void startAction() {
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedReader reader2 = null;
        try {
            socket = new Socket("127.0.0.1", 8081);
            reader = new BufferedReader(new InputStreamReader(System.in));
            reader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            readSocketInfo(reader2);
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

    /**
     * 开户新线程读取数据
     * @param reader
     */
    private void readSocketInfo(BufferedReader reader) {
        new Thread(new WorkThread(reader)).start();
    }
}
