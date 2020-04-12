/**
 * Copyright (C), 2015-2020
 * FileName: ByteBufTest
 * Author:   luo.yongqian
 * Date:     2020/4/11 11:02
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 11:02      1.0.0               创建
 */
package com.roboslyq.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);

        System.out.println("init reader index:"+byteBuf.readerIndex());
        System.out.println("init writer index:"+byteBuf.writerIndex());
        System.out.println("init capacity:"+byteBuf.capacity());
        byteBuf.writeBytes("abcdefghijk".getBytes());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.capacity());
//      ---------------------
        print(byteBuf.readBytes(5));
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.capacity());
        byteBuf.clear();
        System.out.println("clear 之后--");
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.capacity());
        // 重新设置写(必须先于read设置)
        byteBuf.writerIndex(12);
        byteBuf.readerIndex(5);

        print(byteBuf.readBytes(5));
    }


    public static void print(ByteBuf byteBuf){
        byte[] b = new byte[byteBuf.capacity()];
        byteBuf.readBytes(b);
        System.out.println(new String(b));
    }

}