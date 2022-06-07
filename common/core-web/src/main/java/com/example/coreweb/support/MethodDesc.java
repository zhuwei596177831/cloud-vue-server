package com.example.coreweb.support;

import java.lang.annotation.*;

/**
 * @author: 朱伟伟
 * @date: 2021-01-06 18:01
 * @description: 方法说明
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface MethodDesc {
    /**
     * @author: 朱伟伟
     * @date: 2021-01-06 18:02
     * @description: 方法注释说明
     **/
    String value();
}
