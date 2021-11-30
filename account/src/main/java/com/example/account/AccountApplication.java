package com.example.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱伟伟
 * @date 2021-11-30 10:10:37
 * @description 账户服务
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableFeignClients(basePackages = {"com.example"})
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
