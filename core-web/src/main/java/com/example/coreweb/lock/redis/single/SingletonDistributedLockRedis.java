package com.example.coreweb.lock.redis.single;

import com.example.core.threadpool.MyExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 朱伟伟
 * @date 2022-03-09 14:46:07
 * @description redis 分布式锁工具类
 * 只适用于redis单机模式！
 */
@Component
public class SingletonDistributedLockRedis {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<String> UNIQUE_LOCK_VALUE = new ThreadLocal<>();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 结合切面、注解 自动加锁
     *
     * @param singletonRedisLock:
     * @author: 朱伟伟
     * @date: 2022-03-11 23:56
     **/
    public Boolean lock(SingletonRedisLock singletonRedisLock) {
        return lock(singletonRedisLock.key(), singletonRedisLock.timeout(), singletonRedisLock.timeUnit());
    }


    /**
     * 手动加锁
     *
     * @param key:
     * @param timeout:
     * @param unit:
     * @author: 朱伟伟
     * @date: 2022-03-11 23:56
     **/
    public Boolean lock(String key, int timeout, TimeUnit unit) {
        String threadName = Thread.currentThread().getName();
        String value = UUID.randomUUID().toString() + threadName;
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        Boolean result = lock != null && lock;
        if (result) {
            UNIQUE_LOCK_VALUE.set(value);
            //1、启动线程 实现超时补偿机制
            //2、不用补偿，业务代码提交前自行查询锁进行判断 是否提交还是回滚
            compensateTimeout(key, timeout, unit, threadName, value);
        } else {
            String exist = stringRedisTemplate.opsForValue().get(key);
            if (exist != null && exist.equals(UNIQUE_LOCK_VALUE.get())) {
                logger.info("线程：{}取得锁{}成功", threadName, key);
                return true;
            }
        }
        logger.info("线程：{}取得锁{}{}", threadName, key, result ? "成功" : "失败");
        return result;
    }

    /**
     * 结合切面、注解 自动解锁
     *
     * @param singletonRedisLock:
     * @author: 朱伟伟
     * @date: 2022-03-11 23:56
     **/
    public Boolean unLock(SingletonRedisLock singletonRedisLock) {
        return unLock(singletonRedisLock.key());
    }

    /**
     * 手动解锁
     *
     * @param key:
     * @author: 朱伟伟
     * @date: 2022-03-11 23:58
     **/
    public Boolean unLock(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value != null && value.equals(UNIQUE_LOCK_VALUE.get())) {
            logger.info("删除线程：{}的redis锁：{}", Thread.currentThread().getName(), key);
            try {
                return stringRedisTemplate.delete(key);
            } finally {
                UNIQUE_LOCK_VALUE.remove();
            }
        }
        return false;
    }

    private void compensateTimeout(String key, int timeout, TimeUnit unit, String threadName, String value) {
        MyExecutor.execute(() -> {
            for (; ; ) {
                try {
                    unit.sleep(timeout - 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("刷新线程：{}的锁{}的超时时间：{}", threadName, key, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                Boolean existKey = stringRedisTemplate.hasKey(key);
                logger.info("当前锁是否存在？：{}", existKey);
                if (existKey != null && existKey) {
                    String existValue = stringRedisTemplate.opsForValue().get(key);
                    if (existValue != null && existValue.equals(value)) {
                        logger.info("延长线程：{}的redis锁：{} {} {}", threadName, key, timeout, unit.name());
                        stringRedisTemplate.expire(key, timeout, unit);
                    } else {
                        logger.info("结束延长线程：{}的redis锁：{}", threadName, key);
                        break;
                    }
                } else {
                    logger.info("结束延长线程：{}的redis锁：{}", threadName, key);
                    break;
                }
            }
        });
    }


}
