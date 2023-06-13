package com.example.coreweb.cache.standalone.redis.pub_sub.test;

import com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.KeyExpiration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author 朱伟伟
 * @date 2022-03-20 18:42:37
 * @description
 */
@RestController
@RequestMapping("/redis/expire")
public class KeyExpireTest {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/test")
    public void test() {
        stringRedisTemplate.opsForValue().set(KeyExpiration.test.getKey(), KeyExpiration.test.getDesc(), 30, TimeUnit.SECONDS);
    }
}
