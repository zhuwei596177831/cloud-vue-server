package com.example.order;

import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author 朱伟伟
 * @date 2021-11-30 10:11:56
 * @description 订单服务
 */
@SpringBootApplication(
        scanBasePackages = {"com.example"},
        exclude = {ShiroAnnotationProcessorAutoConfiguration.class}
)
@EnableFeignClients(basePackages = {"com.example"})
@EnableAsync
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
