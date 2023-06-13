package com.example.coreweb.cache.standalone.redis.pub_sub.errorHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 * @author 朱伟伟
 * @date 2022-03-18 23:58:12
 * @description
 */
public class LogRedisMessageErrorHandler implements ErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleError(Throwable t) {
        t.printStackTrace();
        logger.error("RedisMessageListenerContainer发布消息异常：{}", t.getMessage());
    }
}
