package com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration.handler;

import org.springframework.data.redis.connection.Message;

/**
 * redis带有有效期key失效事件的处理器
 *
 * @author 朱伟伟
 * @date 2023-06-13 09:42:34
 */
public interface KeyExpirationHandler {

    /**
     * 当前handler是否支持给定的Message
     *
     * @param message 类封装了Redis消息体及其属性。
     * @author 朱伟伟
     * @date 2023-06-13 09:43
     **/
    boolean support(Message message);

    /**
     * 处理给定的Message
     * <p>
     * note：执行当前handler之前，确保{@code support}方法返回true
     *
     * @param message 类封装了Redis消息体及其属性。
     * @author 朱伟伟
     * @date 2023-06-13 09:43
     **/
    void handle(Message message);

}
