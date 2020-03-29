package com.roboslyq.netty.echo.udp;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * 1、概念：
 *      UDP是一种无连接的协议，也就是说在客户端和服务端使用UDP通信的过程中是没有连接的，所以很难说这个消息属于哪个连接。
 * 2、特点：
 *      (1)UDP没有像TCP那样的纠错能力。TCP协议中发送端发送一个包之后，接收端会返回一个消息表示我收到了这个包，当发送端没收到接收端的确认信息时发送端就会重发这个包。
 *         UDP就不是这样的，UDP发送包之后立即就不管了，继续发送下面的包，所以UDP的发送端并不知道接收端有没有收到包，也就是说UDP只管发，
 *         接收端收没收到就是不是它的事了
 * 3、应用场景：
 *      (1)主要适用于高性能且丢部分包不是问题的场景。基于UDP协议的应用有一个著名的例子，就是DNS域名解析服务。
 *      (2)UDP很适合那些数据量大但是丢包又对应用无太大影响的应用情况，例如视频类应用。当然，如果数据准确性很高，
 *          消息不能丢失类型的应用是绝对不能使用UDP的，这种情况应该使用TCP类协议，例如金钱交易类型的应用
 * 4、Netty对UDP的包装
 *      由于Netty提供了统一的API，所以无论使用的是TCP还是UDP，大部分API都是一样的。
 *      你也可以重用你实现的ChannelHandler或者其他基于Netty的实现。
 *
 */
public class EchoUdpServer {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 8888;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        new EchoUdpServer().run(port);
    }


    /**
     *
     * @param port
     * @throws Exception
     */
    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            /*
             * 1、通过NioDatagramChannel创建Channel，并设置Socket参数支持广播
             * 2、UDP相对于TCP不需要在客户端和服务端建立实际的连接，因此不需要为连接（ChannelPipeline）设置handler
             * 3、UDP服务端也是使用BootStrap并且只有一个线程池，而不像TCP具有两个线程池。
             */
            Bootstrap bootStrap = new Bootstrap();
            bootStrap.group(bossGroup)
                    //设置Channel为UDP类型
                    .channel(NioDatagramChannel.class)
                    //这里使用NioDatagramChannel，因为是广播地址所以下面的SO_BROADCAST属性要设置为trueQuoteOfTheMomentServer
                    .option(ChannelOption.SO_BROADCAST, true)
                    //设置channel处理handler
                    .handler(new EchoUdpServerHandler());
            //阻塞
            bootStrap.bind(port).sync()
                    .channel().closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }

}
