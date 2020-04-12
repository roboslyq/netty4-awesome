/**
 * Copyright (C), 2015-2020
 * FileName: Test
 * Author:   luo.yongqian
 * Date:     2020/4/11 12:08
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 12:08      1.0.0               创建
 */
package com.roboslyq.test;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public class Test {
    public static void main(String[] args) {
        //0b00111011_00000000_10101100_01111010
        //0b00111011_00000000_10101100_01111010
        int i = 989899898;
        byte[] b = new byte[4];
        setInt(b,0,i);
        System.out.println(b);
    }
    static void setInt(byte[] memory, int index, int value) {
        memory[index]     = (byte) (value >>> 24);
        memory[index + 1] = (byte) (value >>> 16);
        memory[index + 2] = (byte) (value >>> 8);
        memory[index + 3] = (byte) value;
    }
}