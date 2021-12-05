package com.example.coreweb.controllerAdvice;

import com.example.core.entity.Result;
import com.example.core.entity.ResultCode;
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

    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e) {
        return ResultCode.COMMON.getResult(e.getMessage());
    }

    /**
     * @param e:
     * @author: 朱伟伟
     * @date: 2021-01-14 18:21
     * @description:
     * @see org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor#resolveArgument
     **/
    @ExceptionHandler(java.net.BindException.class)
    public Result MethodArgumentNotValidExceptionHandler(BindException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return ResultCode.COMMON.getResult(objectError.getDefaultMessage());
    }

    /**
     * @param e:
     * @author: 朱伟伟
     * @date: 2021-01-14 18:19
     * @description:
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor#resolveArgument
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return ResultCode.COMMON.getResult(objectError.getDefaultMessage());
    }
    /**
     * @param e:
     * @author: 朱伟伟
     * @date: 2021-01-19 15:03
     * @description:
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor#readWithMessageConverters
     **/
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResultCode.COMMON.getResult(e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public Result exception(Exception e) {
        e.printStackTrace();
        return ResultCode.COMMON.getResult(e.getMessage());
    }


}
