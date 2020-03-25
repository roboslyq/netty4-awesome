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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


/**
 * @author luo.yongqian
 */
public class EchoHttpClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        FullHttpResponse response = (FullHttpResponse) msg;

        ByteBuf content = response.content();
        HttpHeaders headers = response.headers();
        System.out.println("content:" + System.getProperty("line.separator") + content.toString(CharsetUtil.UTF_8));
        System.out.println("headers:" + System.getProperty("line.separator") + headers.toString());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI url = new URI("/test");
        String meg = "hello";

        //配置HttpRequest的请求数据和一些配置信息
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0
                                                            , HttpMethod.GET
                                                            , url.toASCIIString()
                                                            , Unpooled.wrappedBuffer(meg.getBytes(StandardCharsets.UTF_8))
                                                            );
        request.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8")
                //开启长连接
                .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                //设置传递请求内容的长度
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            //发送数据
            ctx.writeAndFlush(request);
    }
}
