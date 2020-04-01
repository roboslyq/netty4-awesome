/**
 * Copyright (C), 2015-2020
 * FileName: StickyServer
 * Author:   luo.yongqian
 * Date:     2020/4/1 22:31
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/1 22:31      1.0.0               创建
 */
package com.roboslyq.netty.sticky;

import com.roboslyq.netty.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 *
 * 〈当客户端发送100次时，一个可能的结果：
 *      第0收到客户请求: hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.hello world, i am a developer.
 *      第1收到客户请求: hello world, i am a developer.hello world, i am a developer.
 *      第2收到客户请求: hello world, i am a developer.
 *      第3收到客户请求: hello world, i am a developer.
 *      第4收到客户请求: hello world, i am a developer.
 *      第5收到客户请求: hello world, i am a developer.
 *      第6收到客户请求: hello world, i am a developer.
 *      第7收到客户请求: hello world, i am a developer.
 *      第8收到客户请求: hello world, i am a developer.
 *      第9收到客户请求: hello world, i am a developer.
 *      第10收到客户请求: hello world, i am a developer.
 *      第11收到客户请求: hello world, i am a developer.
 *      第12收到客户请求: hello world, i am a developer.
 *      第13收到客户请求: hello world, i am a developer.
 *      第14收到客户请求: hello world, i am a developer.
 *      第15收到客户请求: hello world, i am a developer.
 *      第16收到客户请求: hello world, i am a developer.
 *      第17收到客户请求: hello world, i am a developer.
 *      第18收到客户请求: hello world, i am a developer.
 *      第19收到客户请求: hello world, i am a developer.
 *      第20收到客户请求: hello world, i am a developer.
 *      第21收到客户请求: hello world, i am a developer.
 *      第22收到客户请求: hello world, i am a developer.
 *      第23收到客户请求: hello world, i am a developer.
 *      第24收到客户请求: hello world, i am a developer.
 *      第25收到客户请求: hello world, i am a developer.
 *      第26收到客户请求: hello world, i am a developer.
 *      第27收到客户请求: hello world, i am a developer.
 *      第28收到客户请求: hello world, i am a developer.
 *      第29收到客户请求: hello world, i am a developer.
 *      第30收到客户请求: hello world, i am a developer.
 *      第31收到客户请求: hello world, i am a developer.
 *      第32收到客户请求: hello world, i am a developer.
 *      第33收到客户请求: hello world, i am a developer.
 *      第34收到客户请求: hello world, i am a developer.
 *      第35收到客户请求: hello world, i am a developer.
 *      第36收到客户请求: hello world, i am a developer.
 *      第37收到客户请求: hello world, i am a developer.
 *      第38收到客户请求: hello world, i am a developer.
 *      第39收到客户请求: hello world, i am a developer.
 *      第40收到客户请求: hello world, i am a developer.
 *      第41收到客户请求: hello world, i am a developer.
 *      第42收到客户请求: hello world, i am a developer.
 *      第43收到客户请求: hello world, i am a developer.
 *      第44收到客户请求: hello world, i am a developer.
 *      第45收到客户请求: hello world, i am a developer.
 *      第46收到客户请求: hello world, i am a developer.
 *      第47收到客户请求: hello world, i am a developer.
 *      第48收到客户请求: hello world, i am a developer.
 *      第49收到客户请求: hello world, i am a developer.
 *      第50收到客户请求: hello world, i am a developer.
 *      第51收到客户请求: hello world, i am a developer.
 *      第52收到客户请求: hello world, i am a developer.
 *      第53收到客户请求: hello world, i am a developer.
 *      第54收到客户请求: hello world, i am a developer.
 *      第55收到客户请求: hello world, i am a developer.
 *      第56收到客户请求: hello world, i am a developer.
 *      第57收到客户请求: hello world, i am a developer.
 *      第58收到客户请求: hello world, i am a developer.
 *      第59收到客户请求: hello world, i am a developer.
 *      第60收到客户请求: hello world, i am a developer.
 *      第61收到客户请求: hello world, i am a developer.
 *      第62收到客户请求: hello world, i am a developer.
 *      第63收到客户请求: hello world, i am a developer.
 *      第64收到客户请求: hello world, i am a developer.
 *      第65收到客户请求: hello world, i am a developer.
 *      第66收到客户请求: hello world, i am a developer.
 *      第67收到客户请求: hello world, i am a developer.
 *      第68收到客户请求: hello world, i am a developer.
 *      第69收到客户请求: hello world, i am a developer.hello world, i am a developer.hell
 *      第70收到客户请求: o world, i am a developer.hello world, i am a developer.
 *      第71收到客户请求: hello world, i am a developer.
 *      第72收到客户请求: hello world, i am a developer.
 *      第73收到客户请求: hello world, i am a developer.
 *      第74收到客户请求: hello world, i am a developer.
 *
 * 〉
 * @author roboslyq
 * @date 2020/4/1
 * @since 1.0.0
 */
public class StickyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup= new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try{

            bootstrap.group(bossGroup,workGroup)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new StickyServerHandlerInitializer());
            InetSocketAddress inetSocketAddress = new InetSocketAddress(Constants.HOST, Constants.PORT);
            ChannelFuture future =bootstrap.bind(inetSocketAddress).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}