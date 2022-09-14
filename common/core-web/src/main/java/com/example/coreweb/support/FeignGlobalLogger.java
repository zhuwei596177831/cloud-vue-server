package com.example.coreweb.support;

import com.example.coreweb.openfeign.LogFeign;
import feign.Request;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 朱伟伟
 * @date 2022-09-14 09:59:18
 * @description 自定义Logger之feign调用打印
 * <p/>
 * 方式一：配置一个全局唯一的Logger
 * @see org.springframework.cloud.openfeign.FeignContext
 * @see org.springframework.cloud.openfeign.FeignClientsConfiguration
 * @see org.springframework.cloud.openfeign.FeignLoggerFactory
 */
@Component
public class FeignGlobalLogger extends feign.Logger {

    @Autowired
    private LogFeign logFeign;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //打印请求
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        super.logRequest(configKey, logLevel, request);
    }

    //打印响应
    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String format = String.format("%s耗时%s毫秒", configKey, elapsedTime);
        logger.info(format);
        logger.info("configKey:{}", configKey);//TestFeign#test(String)
        return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
    }

    //通用打印 logRequest logAndRebufferResponse会调用
    @Override
    protected void log(String configKey, String format, Object... args) {
        logger.info(String.format(methodTag(configKey) + format, args));
    }
}
