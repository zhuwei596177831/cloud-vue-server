package com.example.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 朱伟伟
 * @date 2022-05-30 14:36:50
 * @description
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
