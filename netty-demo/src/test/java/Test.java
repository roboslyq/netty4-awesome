/**
 * Copyright (C), 2015-2020
 * FileName: Test
 * Author:   luo.yongqian
 * Date:     2020/3/26 16:29
 * Description:
 * History:
 * <author>                 <time>          <version>          <desc>
 * luo.yongqian         2020/3/26 16:29      1.0.0               创建
 */

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * 〈〉
 * @author luo.yongqian
 * @date 2020/3/26
 * @since 1.0.0
 */
public class Test {


    public static void main(String[] args) {
        int a = 0;
        int b = 1 << a;
        int c = 1 << 2;
        System.out.println(b);
        Test test = new Test();
        test.test("String");
        User1 user1 = new User1("hello world");
        ParameterizedType parameterizedType = (ParameterizedType)user1.getClass().getGenericSuperclass();
        Type type = parameterizedType.getActualTypeArguments()[0];
        Type type1 = parameterizedType.getActualTypeArguments()[1];
        Type type2 = parameterizedType.getActualTypeArguments()[2];
        System.out.println(type.getTypeName().equals("java.lang.String"));
        System.out.println(type1);
        System.out.println(type2);
        System.out.println(user1.getVal(0,0));
    }

    public <T> T test(T t){
        System.out.println(t);
        return t;
    }
}

class User<T,T1,T2>{

    private T t;

    public User(T t) {
        this.t = t;
    }

    public T getVal(T1 t1, T2 t2){
        return t;
    }

    public <T3> T3 test(T3 t){
        System.out.println(t);
        return t;
    }
}

class User1 extends User<String,Integer,Integer>{

    public User1(String s) {
        super(s);
    }

    @Override
    public <T3> T3 test(T3 t){
        System.out.println(t);
        return t;
    }
}