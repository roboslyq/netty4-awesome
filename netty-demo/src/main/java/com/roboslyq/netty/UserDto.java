/**
 * Copyright (C), 2015-2020
 * FileName: UserDto
 * Author:   luo.yongqian
 * Date:     2020/4/11 22:46
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/4/11 22:46      1.0.0               创建
 */
package com.roboslyq.netty;

import java.security.SecureRandomSpi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 〈〉
 * @author roboslyq
 * @date 2020/4/11
 * @since 1.0.0
 */
public class UserDto {
    Long id;
    String name;
    String password;
    List list = new ArrayList<>();
    Map<String,String> hashMap = new HashMap();
    public UserDto(){
        this.id = 1L;
        list.add(1);
        hashMap.put("1","1");
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}