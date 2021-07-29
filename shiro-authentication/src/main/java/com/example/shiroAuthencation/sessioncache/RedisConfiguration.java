package com.example.shiroAuthencation.sessioncache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

/**
 * @author 朱伟伟
 * @date 2021-05-24 11:44:25
 * @description RedisConfiguration
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisSessionTemplate redisSessionTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new RedisSessionTemplate(redisConnectionFactory);
    }

}
