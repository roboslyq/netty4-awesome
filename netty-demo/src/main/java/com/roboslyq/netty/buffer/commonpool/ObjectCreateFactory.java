/**
 * Copyright (C), 2015-2020
 * FileName: ObjectCreateUtils
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:38
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:38      1.0.0               创建
 */
package com.roboslyq.netty.buffer.commonpool;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public interface ObjectCreateFactory<T> {
    public T create();
    public void destroy();
}