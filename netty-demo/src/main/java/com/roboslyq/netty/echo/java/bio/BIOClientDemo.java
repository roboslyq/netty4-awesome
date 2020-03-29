/**
 * Copyright (C), 2015-2020
 * FileName: BIOClientDemo
 * Author:   luo.yongqian
 * Date:     2020/3/28 0:37
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/28 0:37      1.0.0               创建
 */
package com.roboslyq.netty.echo.java.bio;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * 〈〉
 * @author luo.yongqian
 * @date 2020/3/28
 * @since 1.0.0
 */
public class BIOClientDemo {
   public static Executor executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1",8081);
        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        rev(reader);
        while (true){
            String req = sysin.readLine();
            if(req != null){
                writer.write("req: " + req + "\n");
                writer.flush();
            }
        }
    }

    public static void rev(BufferedReader reader) throws Exception{
        executor.execute(()->{
            while (true){
                try {
                    String rev ="";

                    if (!Objects.isNull(rev = reader.readLine())){
                        System.out.println("从服务器端接收到处理结果： " + rev);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}