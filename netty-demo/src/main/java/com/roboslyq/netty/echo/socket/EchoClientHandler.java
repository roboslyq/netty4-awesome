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
package com.roboslyq.netty.echo.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


public class EchoClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     *
     * @param ctx 上下文请求对象
     * @param msg 表示服务端发来的消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到服务端" + ctx.channel().remoteAddress()+ "消息: "+msg);
        TimeUnit.SECONDS.sleep(1);
        ctx.writeAndFlush("来自 client："+ LocalDateTime.now());
    }


    /**
     * 0、此方法只会被激活一次
     * 1、如果没有这个方法，Client并不会主动发消息给Server，那么Server的channelRead0无法触发，导致Client的channelRead0也无法触发
     * 2、这个channelActive可以让Client连接后，发送一条消息给服务器端，才能触发上面的channelRead0()方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        TimeUnit.SECONDS.sleep(1);
        ctx.writeAndFlush("来自客户端的问候");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
