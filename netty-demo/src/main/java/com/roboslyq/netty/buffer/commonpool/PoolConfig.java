/**
 * Copyright (C), 2015-2020
 * FileName: PoolConfig
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:29
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:29      1.0.0               创建
 */
package com.roboslyq.netty.buffer.commonpool;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public class PoolConfig {
    public int minSize;
    public int maxSize;

    public PoolConfig(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}