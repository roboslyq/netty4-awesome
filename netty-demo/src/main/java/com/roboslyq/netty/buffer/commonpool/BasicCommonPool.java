/**
 * Copyright (C), 2015-2020
 * FileName: BasicCommonPool
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:28
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:28      1.0.0               创建
 */
package com.roboslyq.netty.buffer.commonpool;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public class BasicCommonPool<T> extends AbstractCommonPool<T>
{
    private LinkedBlockingQueue<T> pool;

    public BasicCommonPool(PoolConfig poolConfig, ObjectCreateFactory clazz) {
        super(poolConfig, clazz);
        init();
    }

    @Override
    public T get() {
        return pool.poll();
    }

    @Override
    public void put(T o) {
        try {
            pool.put(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        //池容量默认最小，最大扩容待实现
       this.pool = new LinkedBlockingQueue<T>(this.poolConfig.minSize);
        for (int i = 0; i < this.poolConfig.maxSize; i++) {
            try {
                pool.put(clazz.create());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}