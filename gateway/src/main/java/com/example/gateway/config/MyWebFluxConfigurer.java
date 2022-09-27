package com.example.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author 朱伟伟
 * @date 2022-09-27 17:00:34
 * @description webflux 自定义配置
 */
@Configuration(proxyBeanMethods = false)
public class MyWebFluxConfigurer implements WebFluxConfigurer {

}
