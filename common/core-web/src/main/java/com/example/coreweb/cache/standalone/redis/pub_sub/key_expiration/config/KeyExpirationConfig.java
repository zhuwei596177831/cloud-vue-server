package com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.config;

import com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.handler.KeyExpirationEventMessageProcessor;
import com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.handler.KeyExpirationHandler;
import com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.handler.KeyExpirationHandlerComposite;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * 单机模式下，监听redis带有有效期key的失效事件的配置类
 * <p>
 * 内部实际上通过PatternTopic实现，Message的channel（__keyevent@*__:expired）与redis的database有关，
 * 例如：默认database是0，则channel是固定值：__keyevent@0__:expired
 * 所以:默认监听redis所有失效key的实效事件，无法单独监听某一个指定key的失效事件
 *
 * @author 朱伟伟
 * @date 2023-06-13 09:51:34
 */
@Configuration(proxyBeanMethods = false)
public class KeyExpirationConfig {

    /**
     * 方式一：往容器中添加自己handler，继承自{@link KeyExpirationEventMessageListener}，
     * 将具体的业务逻辑分给各自的{@link KeyExpirationHandler}
     *
     * @param redisMessageListenerContainer 负责分发redis发布/订阅机制的容器
     * @param keyExpirationHandlerProvider  容器中所有的KeyExpirationHandler
     * @author 朱伟伟
     * @date 2023-06-13 10:20
     **/
    @Bean
    public KeyExpirationEventMessageListener keyExpirationEventMessageListener(RedisMessageListenerContainer redisMessageListenerContainer,
                                                                               ObjectProvider<KeyExpirationHandler[]> keyExpirationHandlerProvider) {
        KeyExpirationEventMessageProcessor keyExpirationEventMessageProcessor = new KeyExpirationEventMessageProcessor(redisMessageListenerContainer);
        KeyExpirationHandler[] keyExpirationHandlers = keyExpirationHandlerProvider.getIfAvailable();
        KeyExpirationHandlerComposite keyExpirationHandlerComposite = new KeyExpirationHandlerComposite(keyExpirationHandlers);
        keyExpirationEventMessageProcessor.setKeyExpirationHandlerComposite(keyExpirationHandlerComposite);
        return keyExpirationEventMessageProcessor;
    }

    /**
     * 方式二：直接往容器中添加{@link KeyExpirationEventMessageListener}，
     * 通过{@link org.springframework.context.ApplicationListener}
     * 或者{@link org.springframework.context.event.EventListener}注解
     * 监听{@link org.springframework.data.redis.core.RedisKeyExpiredEvent}事件
     *
     * @param redisMessageListenerContainer 负责分发redis发布/订阅机制的容器
     * @author 朱伟伟
     * @date 2023-06-13 10:23
     **/
//    @Bean
//    public KeyExpirationEventMessageListener keyExpirationEventMessageListener(RedisMessageListenerContainer redisMessageListenerContainer) {
//        return new KeyExpirationEventMessageListener(redisMessageListenerContainer);
//    }

}
