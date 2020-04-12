/**
 * Copyright (C), 2015-2020
 * FileName: ICommonPool
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:24
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:24      1.0.0               创建
 */
package com.roboslyq.netty.buffer.commonpool;

/**
 *
 * 〈对象池〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public interface CommonPool<T> {
    public T get();
    public void put(T t);
}