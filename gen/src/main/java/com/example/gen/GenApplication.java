package com.example.gen;

import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 朱伟伟
 * @date 2022-06-01 11:02:45
 * @description
 */
@SpringBootApplication(
        scanBasePackages = {"com.example"},
        //排除shiro注解权限功能的自动配置类，去除多级代理
        //(也可以在yml文件配置shiro.annotations.enabled=false)
        exclude = {ShiroAnnotationProcessorAutoConfiguration.class}
)
public class GenApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenApplication.class, args);
    }

}
