package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱伟伟
 * @date 2021-07-07 14:13:57
 * @description
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableFeignClients(basePackages = {
        "com.example.system.openFeign"
})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
