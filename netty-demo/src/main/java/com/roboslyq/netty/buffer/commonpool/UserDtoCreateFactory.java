/**
 * Copyright (C), 2015-2020
 * FileName: UserDtoCreateFactory
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:47
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:47      1.0.0               创建
 */
package com.roboslyq.netty.buffer.commonpool;

import com.roboslyq.netty.UserDto;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public class UserDtoCreateFactory implements ObjectCreateFactory<UserDto> {

    @Override
    public UserDto create() {
        return new UserDto();
    }

    @Override
    public void destroy() {
        System.out.println("destoryed...");

    }
}