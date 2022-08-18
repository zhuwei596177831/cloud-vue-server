package com.example.coreweb.encrypt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * @author 朱伟伟
 * @date 2022-08-01 09:54:04
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class MyEncryptorBootstrapConfig {

    @Bean
    public TextEncryptor textEncryptor() {
        return new MyEncryptor();
    }


}
