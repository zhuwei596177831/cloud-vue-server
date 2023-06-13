package com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.handler;

/**
 * 处理redis客户端发布的消息的策略接口
 *
 * @author 朱伟伟
 * @date 2022-03-19 11:19:16
 * @see com.example.coreweb.cache.standalone.redis.pub_sub.message_listener.config.MessageListenerAdapterConfig
 * @see org.springframework.data.redis.listener.adapter.MessageListenerAdapter
 */
public interface MessageListenerHandler<T> {

    /**
     * 接收redis客户端发布的消息
     * <p>方法名不可更改</p>
     *
     * @param message 消息内容
     * @param channel {@link com.example.coreweb.cache.standalone.redis.pub_sub.annotation.RedisTopic}对应的name
     * @author 朱伟伟
     * @date 2023-06-12 16:35
     **/
    void handleMessage(T message, String channel);

}
