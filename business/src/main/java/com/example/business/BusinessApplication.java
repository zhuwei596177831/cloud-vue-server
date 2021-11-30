package com.example.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱伟伟
 * @date 2021-11-30 10:19:02
 * @description 业务服务
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableFeignClients(basePackages = {"com.example"})
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

}
