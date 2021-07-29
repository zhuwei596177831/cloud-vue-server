package com.example.coreweb.support;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * @author 朱伟伟
 * @date 2021-02-12 15:19:04
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * @author: 朱伟伟
     * @date: 2021-01-21 10:28
     * @description: springboot方式配置自定义的 {@link HttpMessageConverter}使其放在最前面
     * @see org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration#messageConverters
     * @see org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#configureMessageConverters
     **/
//    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //限制只处理 application/json Content-Type类型的数据转换  默认*/* !!!!!
        fastJsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //String null 序列化为 ""
//                SerializerFeature.WriteNullStringAsEmpty,
                //集合 null 序列化为[]
//                SerializerFeature.WriteNullListAsEmpty,
                //序列化null字段
//                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat
        );
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return fastJsonHttpMessageConverter;
    }


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedHeaders("user_sign")
//                .allowCredentials(true)
//                .exposedHeaders("user_sign")
//                .maxAge(3600);
//    }

}
