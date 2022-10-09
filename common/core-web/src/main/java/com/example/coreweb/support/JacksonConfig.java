package com.example.coreweb.support;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-07-09 17:07:27
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class JacksonConfig {

    /**
     * Jackson全局转化long类型为String，解决jackson序列化时long类型 JS精度缺失问题
     *
     * @author: 朱伟伟
     * @date: 2021-07-08 18:27
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#configureMessageConverters(List)
     **/
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .serializerByType(Long.class, ToStringSerializer.instance)
                //忽略 反序列化时 实体中有自定的get方法的报错
                .failOnUnknownProperties(false)
                .serializers()
                ;
    }

}
