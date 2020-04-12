/**
 * Copyright (C), 2015-2020
 * FileName: AbstractCommonPool
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:26
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:26      1.0.0               创建
 */
package com.roboslyq.netty.buffer.commonpool;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public abstract class AbstractCommonPool<T> implements CommonPool<T> {
    public PoolConfig poolConfig;
    public ObjectCreateFactory<T> clazz;

    public AbstractCommonPool(PoolConfig poolConfig, ObjectCreateFactory<T> clazz) {
        this.poolConfig = poolConfig;
        this.clazz = clazz;
    }

    public PoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(PoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public ObjectCreateFactory<T> getClazz() {
        return clazz;
    }

    public void setClazz(ObjectCreateFactory<T> clazz) {
        this.clazz = clazz;
    }
}