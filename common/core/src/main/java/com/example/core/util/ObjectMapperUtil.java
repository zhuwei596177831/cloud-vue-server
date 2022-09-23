package com.example.core.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author 朱伟伟
 * @date 2022-03-19 10:55:13
 * @description ObjectMapper 单例类
 */
public class ObjectMapperUtil {

    private ObjectMapperUtil() {

    }

    private static class ObjectMapperHolder {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        static {
            //设置时区 东八区
            objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            //date类型 全局配置格式
            objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
            //配置 java8的支持
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.registerModule(new Jdk8Module());
            //忽略 反序列化时 实体中有自定的get方法的报错
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }

    private static class RedisObjectMapperHolder {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        static {
            //设置时区 东八区
            objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            //date类型 全局配置格式
            objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
            //配置 java8的支持
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.registerModule(new Jdk8Module());
            //忽略 反序列化时 实体中有自定的get方法的报错
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
            objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        }
    }

    public static ObjectMapper instance() {
        return ObjectMapperHolder.objectMapper;
    }

    public static ObjectMapper instanceForRedis() {
        return RedisObjectMapperHolder.objectMapper;
    }


}
