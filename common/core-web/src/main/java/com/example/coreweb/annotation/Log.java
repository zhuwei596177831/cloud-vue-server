package com.example.coreweb.annotation;


import com.example.coreweb.enums.BusinessType;
import com.example.coreweb.enums.LogType;
import com.example.coreweb.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * </p>
 * 暂时只能标记在业务controller层方法！！！
 *
 * @author: 朱伟伟
 * @date: 2022-06-07 15:11
 * @see com.example.coreweb.aspect.LogAspect
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 日志类型 默认：操作日志
     */
    LogType type() default LogType.OPERATION;

    /**
     * 所属模块
     */
    String title();

    /**
     * 操作类型 新增、修改、删除等等
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别 web、移动端等等
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;
}
