/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.roboslyq.netty.echo.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * 通过Netty实现HttpClient,也可以直接使用浏览器等工具向服务器端发送Http请求。
 */
public final class EchoHttpClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8081"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {


        // 客户端工作线程池
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            Bootstrap handler = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            /*
                             * 关于Pipeline配置，客户端与服务端必须保持一个对应关系，才能正常的实现编码解码操作。
                             */
                            ChannelPipeline p = ch.pipeline();
                            //Http客户端编码/解码器
                            p.addLast(new HttpClientCodec());
                            //Http请求聚合器
                            p.addLast(new HttpObjectAggregator(1024 * 10 * 1024));
                            //Http解压器
                            p.addLast(new HttpContentDecompressor());
                            //自定义业务处理器(类似于springmvc中的DispatcherServlet)
                            p.addLast(new EchoHttpClientHandler());
                        }
                    });

            // Start the clientStart.
            // 启动客户端
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // Wait until the connection is closed.
            // 监听客户端直到连接关闭
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            // 优雅的关闭线程池
            group.shutdownGracefully();
        }
    }
}
