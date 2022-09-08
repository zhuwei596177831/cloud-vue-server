package com.example.coreweb.support;

import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author 朱伟伟
 * @date 2022-08-19 15:32:27
 * @description 自定义Logger之feign调用打印
 */
//@Component
public class FeignExecutionTimeLogger extends feign.Logger {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void log(String configKey, String format, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format(methodTag(configKey) + format, args));
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String format = String.format("%s耗时%s毫秒", configKey, elapsedTime);
        logger.info(format);
        return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
    }
}
