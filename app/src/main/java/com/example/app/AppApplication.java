package com.example.app;

import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication(
        scanBasePackages = {"com.example"},
        //排除shiro注解权限功能的自动配置类，去除多级代理
        //(也可以在yml文件配置shiro.annotations.enabled=false)
        exclude = {ShiroAnnotationProcessorAutoConfiguration.class}
)
@EnableFeignClients(basePackages = {"com.example"})
@EnableAsync
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
