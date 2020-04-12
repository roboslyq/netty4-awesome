/**
 * Copyright (C), 2015-2020
 * FileName: TestMain
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:54
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:54      1.0.0               创建
 */
package com.roboslyq.netty.buffer.commonpool;

import com.roboslyq.netty.UserDto;

/**
 *
 * 〈测试对象池，可能对象太简单了，效果不理想呀〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public class TestMain {

    public static void main(String[] args) {
        PoolConfig poolConfig = new PoolConfig(10,10);
        CommonPool<UserDto> commonPool = CommonPoolFactory.create(new UserDtoCreateFactory(),poolConfig);
        Long start1 = System.currentTimeMillis();
        for(int i = 0;i <10000 ; i++){
           UserDto userDto = commonPool.get();
           userDto.setId((long) i);
//            System.out.println(userDto.getId());
           commonPool.put(userDto);
        }
        System.out.println(System.currentTimeMillis() - start1);

        Long start2 = System.currentTimeMillis();
        for(int i = 0;i <10000 ; i++){
            UserDto userDto = new UserDto();
            userDto.setId((long) i);
//            System.out.println(userDto.getId());
        }
        System.out.println(System.currentTimeMillis() - start2);


    }

}