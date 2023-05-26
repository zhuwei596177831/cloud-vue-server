package com.example.coreweb.encrypt;

import org.springframework.cloud.context.encrypt.EncryptorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * @author 朱伟伟
 * @date 2022-08-01 09:54:04
 * @description
 */
public class MyEncryptorBootstrapConfig {

    private static final TextEncryptor TEXT_ENCRYPTOR = new EncryptorFactory().create("zhuweiwei");

    public static void main(String[] args) {
        System.out.println(TEXT_ENCRYPTOR.encrypt("root"));
        System.out.println(TEXT_ENCRYPTOR.encrypt("manager"));
    }

//    @Bean
//    public TextEncryptor textEncryptor() {
//        return TEXT_ENCRYPTOR;
//    }

    @Bean
    public TextEncryptor myRsaEncryptor() {
        return new MyRsaEncryptor();
    }


}
