/**
 * Copyright (C), 2015-2020
 * FileName: CommonPoolFactory
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:27
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:27      1.0.0               创建
 */
package com.roboslyq.netty.buffer.commonpool;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public class CommonPoolFactory {
    public static CommonPool create(ObjectCreateFactory t,PoolConfig config){
        return new BasicCommonPool(config,t);
    }
}