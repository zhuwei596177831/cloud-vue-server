package com.example.coreweb.exception;

import com.example.core.entity.Json;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.validation.ConstraintViolationException;

/**
 * @author 朱伟伟
 * @date 2021-01-06 17:01:06
 * @description 此方法用于初始化@ControllerAdvice标注的Bean，
 * 并解析此Bean内部各部分（@ModelAttribute、@InitBinder、RequestBodyAdvice和ResponseBodyAdvice接口）然后缓存起来。
 * {@link RequestMappingHandlerAdapter#initControllerAdviceCache}
 * {@link RequestMappingHandlerAdapter#getDataBinderFactory}
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理手动抛出的自定义异常ApplicationException
     *
     * @param e:
     * @author: 朱伟伟
     * @date: 2022-05-07 17:58
     **/
    @ExceptionHandler(ApplicationException.class)
    public Json applicationException(ApplicationException e) {
        e.printStackTrace();
        if (e.getResponseCode() != null) {
            return Json.fail(e.getResponseCode().getCode(), e.getMessage());
        }
        return Json.fail(e.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Json constraintViolationExceptionHandler(ConstraintViolationException e) {
        e.printStackTrace();
        return Json.fail(e.getMessage());
    }

    /**
     * @param e:
     * @author: 朱伟伟
     * @date: 2021-01-14 18:21
     * @description:
     * @see org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor#resolveArgument
     **/
    @ExceptionHandler(java.net.BindException.class)
    public Json MethodArgumentNotValidExceptionHandler(BindException e) {
        e.printStackTrace();
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return Json.fail(objectError.getDefaultMessage());
    }

    /**
     * @param e:
     * @author: 朱伟伟
     * @date: 2021-01-14 18:19
     * @description:
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor#resolveArgument
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Json MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return Json.fail(objectError.getDefaultMessage());
    }

    /**
     * @param e:
     * @author: 朱伟伟
     * @date: 2021-01-19 15:03
     * @description:
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor#readWithMessageConverters
     **/
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public Json httpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return Json.fail(e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public Json exception(Exception e) {
        e.printStackTrace();
        return Json.fail(e.getMessage());
    }


}
