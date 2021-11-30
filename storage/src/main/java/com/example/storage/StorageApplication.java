package com.example.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱伟伟
 * @date 2021-11-30 10:12:35
 * @description 仓储服务
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableFeignClients(basePackages = {"com.example"})
public class StorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

}
