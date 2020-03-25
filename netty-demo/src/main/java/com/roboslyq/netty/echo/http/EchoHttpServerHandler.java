/**
 * Copyright (C), 2015-2020
 * FileName: EchoHttpServerHandler
 * Author:   luo.yongqian
 * Date:     2020/3/25 0:25
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/25 0:25      1.0.0               创建
 */
package com.roboslyq.netty.echo.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.UUID;

/**
 *
 * 〈Socket相关Handler，注意泛型类型。此处泛型与EchoServerhandlerInitializer中Pipeline的配置息息相关〉
 * @author luo.yongqian
 * @date 2020/3/25
 * @since 1.0.0
 */
public class EchoHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //如果不加这个判断使用curl 测试会报错，使用curl测试命令curl "http://localhost:8899"
        //判断这个是不是httprequest请求
        if (msg instanceof HttpRequest) {
            System.out.println(msg.getClass());
            System.out.println(ctx.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            //判断url是否请求了favicon.ico
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了favicon.ico");
                return;
            }
            /**
             * 上面这段代码是验证如果用浏览器访问
             * chrome浏览器发起了两次请求，一次是发起的端口，第二次是请求/favicon.ico图标
             * 具体可以查看chrome的请求
             */
            System.out.println("请求方法名:" + httpRequest.method().name());
            //ByteBuf,netty中极为重要的概念，代表响应返回的数据
            ByteBuf content = Unpooled.copiedBuffer("HelloWorld!", CharsetUtil.UTF_8);
            //构造一个http响应,HttpVersion.HTTP_1_1:采用http1.1协议，HttpResponseStatus.OK：状态码200
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //如果只是调用write方法，他仅仅是存在缓冲区里，并不会返回客户端
            //调用writeAndFlush可以
            ctx.writeAndFlush(response);
        }
    }
}