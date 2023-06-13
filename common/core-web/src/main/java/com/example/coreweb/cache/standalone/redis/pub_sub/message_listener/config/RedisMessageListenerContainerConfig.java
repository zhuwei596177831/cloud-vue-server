package com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.config;

import com.example.coreweb.cache.standalone.redis.pub_sub.annotation.RedisTopic;
import com.example.coreweb.cache.standalone.redis.pub_sub.errorHandler.LogRedisMessageErrorHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 实现消息订阅和发布方式一：
 * 配置RedisMessageListener容器，统一负责消息的发送，支持配置线程池，实现异步发送
 * <p>
 * 使用RedisConnectionFactory、StringRedisTemplate、JacksonRedisTemplate方式发布消息
 * 此种方式只能发布和接收String类型的消息，
 * 高级处理方式请查看{@link MessageListenerAdapterConfig}
 * </p>
 *
 * @author 朱伟伟
 * @date 2022-03-18 23:34:13
 */
//@Configuration(proxyBeanMethods = false)
public class RedisMessageListenerContainerConfig {

    private ObjectProvider<MessageListener[]> messageListenerProvider;

    @Autowired
    public void setMessageListenerProvider(ObjectProvider<MessageListener[]> messageListenerProvider) {
        this.messageListenerProvider = messageListenerProvider;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory.getObject());
        redisMessageListenerContainer.setErrorHandler(new LogRedisMessageErrorHandler());
        redisMessageListenerContainer.setTaskExecutor(createRunningMessageListenerTaskExecutor());
        redisMessageListenerContainer.setSubscriptionExecutor(createSubscriptionExecutor());
        MessageListener[] messageListeners = messageListenerProvider.getIfAvailable();
        if (!ObjectUtils.isEmpty(messageListeners)) {
            for (MessageListener messageListener : messageListeners) {
                RedisTopic redisTopic = messageListener.getClass().getDeclaredAnnotation(RedisTopic.class);
                if (redisTopic != null) {
                    String channel = redisTopic.channel();
                    Class<? extends Topic> type = redisTopic.topic();
                    Topic topic = type == ChannelTopic.class ? new ChannelTopic(channel) : new PatternTopic(channel);
                    redisMessageListenerContainer.addMessageListener(messageListener, topic);
                }
            }
        }
        return redisMessageListenerContainer;
    }

    private TaskExecutor createRunningMessageListenerTaskExecutor() {
        ThreadPoolTaskExecutor runningMessageListenerTaskExecutor = new ThreadPoolTaskExecutor();
        runningMessageListenerTaskExecutor.setThreadNamePrefix("pool-redis-running-message-listener-");
        runningMessageListenerTaskExecutor.setCorePoolSize(50);
        runningMessageListenerTaskExecutor.setMaxPoolSize(100);
        runningMessageListenerTaskExecutor.setKeepAliveSeconds(60);
        runningMessageListenerTaskExecutor.setQueueCapacity(100);
        runningMessageListenerTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        runningMessageListenerTaskExecutor.afterPropertiesSet();
        return runningMessageListenerTaskExecutor;
    }

    private TaskExecutor createSubscriptionExecutor() {
        ThreadPoolTaskExecutor subscriptionExecutor = new ThreadPoolTaskExecutor();
        subscriptionExecutor.setThreadNamePrefix("pool-redis-subscribe-");
        subscriptionExecutor.setCorePoolSize(50);
        subscriptionExecutor.setMaxPoolSize(100);
        subscriptionExecutor.setKeepAliveSeconds(60);
        subscriptionExecutor.setQueueCapacity(100);
        subscriptionExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        subscriptionExecutor.afterPropertiesSet();
        return subscriptionExecutor;
    }


}
