package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱伟伟
 * @date 2021-11-30 10:11:56
 * @description 订单服务
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableFeignClients(basePackages = {"com.example"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
