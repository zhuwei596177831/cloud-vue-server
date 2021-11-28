package com.example.gateway;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import java.util.*;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-06-01 22:22
     * @description: gateway使用openfeign时配置HttpMessageConverters
     * @see EnableFeignClients
     * @see FeignClientsConfiguration
     **/
    @Bean
    public HttpMessageConverters httpMessageConverters() {
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new ByteArrayHttpMessageConverter());
        httpMessageConverters.add(new StringHttpMessageConverter());
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder.json().build()));
        return new HttpMessageConverters(httpMessageConverters);
    }

    /**
     * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
     *
     * @author: 朱伟伟
     * @date: 2021-07-08 18:27
     **/
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
    }

}
