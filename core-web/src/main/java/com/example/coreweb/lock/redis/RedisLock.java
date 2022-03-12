package com.example.coreweb.lock.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * redis 分布式锁 注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * redis 锁的key
     */
    String key();

    /**
     * 锁超时时间
     */
    int timeout();

    /**
     * 超时单位 默认：秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
