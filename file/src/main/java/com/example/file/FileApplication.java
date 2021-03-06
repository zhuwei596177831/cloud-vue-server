package com.example.file;

import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 朱伟伟
 * @date 2022-05-30 14:36:50
 * @description
 */
@SpringBootApplication(
        scanBasePackages = {"com.example"},
        //排除shiro注解权限功能的自动配置类，去除多级代理
        //(也可以在yml文件配置shiro.annotations.enabled=false)
        exclude = {ShiroAnnotationProcessorAutoConfiguration.class}
)
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
