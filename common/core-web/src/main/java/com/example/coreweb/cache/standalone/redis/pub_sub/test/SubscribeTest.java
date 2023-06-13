package com.example.coreweb.cache.standalone.redis.pub_sub.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author 朱伟伟
 * @date 2022-03-18 22:49:44
 * @description
 */
//@Component
public class SubscribeTest implements ApplicationRunner {

    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RedisConnection testRedisConnection = redisConnectionFactory.getConnection();
        testRedisConnection.subscribe((message, pattern) -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (pattern != null) {
                System.out.println("收到发布的pattern：" + new String(pattern));
            }
            System.out.println("收到发布的message：");
            System.out.println("1、channel：" + new String(message.getChannel()));
            System.out.println("2、body：" + new String(message.getBody()));
        }, "test".getBytes(StandardCharsets.UTF_8));
        RedisConnection helloRedisConnection = redisConnectionFactory.getConnection();
        helloRedisConnection.subscribe((message, pattern) -> {
            if (pattern != null) {
                System.out.println("收到发布的pattern：" + new String(pattern));
            }
            System.out.println("收到发布的message：");
            System.out.println("1、channel：" + new String(message.getChannel()));
            System.out.println("2、body：" + new String(message.getBody()));
        }, "hello".getBytes(StandardCharsets.UTF_8));

//        RedisConnection testPatternRedisConnection1 = redisConnectionFactory.getConnection();
//        testPatternRedisConnection1.pSubscribe(testMessageListener, "t?st".getBytes(StandardCharsets.UTF_8));
//        testPatternRedisConnection1.pSubscribe(testMessageListener, "t*st".getBytes(StandardCharsets.UTF_8));

        //如下这么写是错误的  RedisConnection与subscribe是一对一的关系
        //RedisConnection redisConnection = redisConnectionFactory.getConnection();
        //redisConnection.pSubscribe(helloMessageListener, RedisChannel.hello.getName().getBytes());
        //redisConnection.pSubscribe(testMessageListener, RedisChannel.test.getName().getBytes());
    }
}
