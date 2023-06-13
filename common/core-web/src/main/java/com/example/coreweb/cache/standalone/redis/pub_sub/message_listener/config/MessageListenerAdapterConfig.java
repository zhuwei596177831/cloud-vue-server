package com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.config;

import com.example.coreweb.cache.standalone.redis.JacksonRedisTemplate;
import com.example.coreweb.cache.standalone.redis.pub_sub.annotation.RedisTopic;
import com.example.coreweb.cache.standalone.redis.pub_sub.errorHandler.LogRedisMessageErrorHandler;
import com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.handler.MessageListenerHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 实现消息订阅和发布方式二：
 * 1、配置RedisMessageListenerContainer、MessageListenerAdapter、Executor实现消息的异步处理
 * 2、将接受消息的业务处理逻辑单独抽出来
 * 3、支持String、POJO、Map，JSONObject的方式接收message
 * <p>
 * 使用RedisConnectionFactory、StringRedisTemplate、JacksonRedisTemplate方式发布消息
 * </p>
 *
 * @author 朱伟伟
 * @date 2022-03-19 11:02:48
 * @see com.sourcecode.spring.aop.cache.redis_pub_sub.test.PublishTest
 */
@SuppressWarnings("all")
@Configuration(proxyBeanMethods = false)
public class MessageListenerAdapterConfig {

    private JacksonRedisTemplate jacksonRedisTemplate;
    private ObjectProvider<MessageListenerHandler[]> messageListenerHandlerProvider;

    @Autowired
    public void setMessageListenerHandlerProvider(ObjectProvider<MessageListenerHandler[]> messageListenerHandlerProvider) {
        this.messageListenerHandlerProvider = messageListenerHandlerProvider;
    }

    @Autowired
    public void setJacksonRedisTemplate(JacksonRedisTemplate jacksonRedisTemplate) {
        this.jacksonRedisTemplate = jacksonRedisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory.getObject());
        redisMessageListenerContainer.setErrorHandler(new LogRedisMessageErrorHandler());
        redisMessageListenerContainer.setTaskExecutor(createRunningMessageListenerTaskExecutor());
        redisMessageListenerContainer.setSubscriptionExecutor(createSubscriptionExecutor());
        MessageListenerHandler[] messageListenerHandlers = messageListenerHandlerProvider.getIfAvailable();
        if (!ObjectUtils.isEmpty(messageListenerHandlers)) {
            for (MessageListenerHandler messageListenerHandler : messageListenerHandlers) {
                RedisTopic redisTopic = messageListenerHandler.getClass().getDeclaredAnnotation(RedisTopic.class);
                if (redisTopic != null) {
                    String channel = redisTopic.channel();
                    Class<? extends Topic> type = redisTopic.topic();
                    Topic topic = type == ChannelTopic.class ? new ChannelTopic(channel) : new PatternTopic(channel);
                    MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(messageListenerHandler);
                    Class<?> requiredType = GenericTypeResolver.resolveTypeArgument(messageListenerHandler.getClass(), MessageListenerHandler.class);
                    if (requiredType != null && requiredType != Void.class) {
                        //字符串类型的数据使用默认的RedisSerializer.string()
                        //Map、POJO使用jackson进行反序列化
                        if (!requiredType.isAssignableFrom(String.class)) {
                            messageListenerAdapter.setSerializer(jacksonRedisTemplate.getValueSerializer());
                        }
                        messageListenerAdapter.afterPropertiesSet();
                        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, topic);
                    }
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
