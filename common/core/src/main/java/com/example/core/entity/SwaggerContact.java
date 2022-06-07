package com.example.core.entity;

import springfox.documentation.service.Contact;

/**
 * @author 朱伟伟
 * @date 2021-05-21 16:07:11
 * @description 单例的Swagger联系人信息
 */
public class SwaggerContact extends Contact {

    private SwaggerContact(String name, String url, String email) {
        super(name, url, email);
    }

    private static class SwaggerContactHolder {
        private static final SwaggerContact INSTANCE = new SwaggerContact("朱伟伟", "596177831@qq.com", "596177831@qq.com");
    }

    public static SwaggerContact getInstance() {
        return SwaggerContactHolder.INSTANCE;
    }

}
