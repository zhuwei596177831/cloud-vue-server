package com.example.coreweb.lock.redis.single;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2022-03-11 18:01:32
 * @description 切面+自定义注解 实现 redis 分布式锁
 */
@Aspect
@Component
public class SingletonRedisLockAspect {

    @Autowired
    private SingletonDistributedLockRedis singletonDistributedLockRedis;

    /**
     * 加锁
     */
    @Before(value = "@annotation(singletonRedisLock)")
    public void lock(JoinPoint joinPoint, SingletonRedisLock singletonRedisLock) {
        Boolean lock = singletonDistributedLockRedis.lock(singletonRedisLock);
        if (!lock) {
            throw new RuntimeException("加锁失败");
        }
    }

    /**
     * 解锁
     */
    @After(value = "@annotation(singletonRedisLock)")
    public void unlock(JoinPoint joinPoint, SingletonRedisLock singletonRedisLock) {
        singletonDistributedLockRedis.unLock(singletonRedisLock);
    }

}


