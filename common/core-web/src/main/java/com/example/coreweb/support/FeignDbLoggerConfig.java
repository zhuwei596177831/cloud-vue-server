package com.example.coreweb.support;

import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @author 朱伟伟
 * @date 2022-09-13 10:45:40
 * @description 自定义Logger之feign调用打印
 * </p>
 * 方式二：为每一个FeignContent配置各自的FeignLoggerFactory
 */
public class FeignDbLoggerConfig {

    @Bean
    public FeignLoggerFactory feignDbLoggerFactory() {
        return new FeignDbLoggerFactory();
    }

}
