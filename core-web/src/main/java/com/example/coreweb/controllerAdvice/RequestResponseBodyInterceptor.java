package com.example.coreweb.controllerAdvice;

import com.example.coreweb.support.MethodDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author 朱伟伟
 * @date 2021-01-06 17:36:56
 * @description RequestBodyAdvice ResponseBodyAdvice 拦截@RequestBody @ResponseBody
 * 必须配合@ControllerAdvice一起使用
 */
@ControllerAdvice(annotations = {RestController.class})
public class RequestResponseBodyInterceptor extends RequestBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseBodyInterceptor.class);

    @Autowired
    ConversionService mvcConversionService;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(RequestBody.class);
    }

    /**
     * @param inputMessage:  request消息
     * @param parameter:     方法参数
     * @param targetType:    方法类型
     * @param converterType: HttpMessageConverter
     * @author: 朱伟伟
     * @date: 2021-01-18 16:48
     * @description: read之前
     **/
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }

    /**
     * @param body:          read后的body
     * @param inputMessage:  request消息
     * @param parameter:     方法参数
     * @param targetType:    方法类型
     * @param converterType: HttpMessageConverter
     * @author: 朱伟伟
     * @date: 2021-01-18 16:42
     * @description: read之后
     **/
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (parameter.hasMethodAnnotation(MethodDesc.class)) {
            MethodDesc methodDesc = parameter.getMethodAnnotation(MethodDesc.class);
            if (methodDesc != null) {
                logger.info("{}接收:\n{}", methodDesc.value(), body);
            }
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        if (mvcConversionService.canConvert(String.class, parameter.getParameterType())) {
//            return mvcConversionService.convert(ResultCode.REQUEST_BODY_MISSING.getResult().toString(), parameter.getParameterType());
//        }
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = returnType.getMethod();
        if (method == null) {
            return false;
        }
        return true;
//        Class<?> returnClass = method.getReturnType();
//        return BeanUtils.isSimpleProperty(returnClass)
//                || returnClass.isAssignableFrom(Collection.class)
//                || returnClass.isAssignableFrom(Map.class)
//                || returnClass.isAssignableFrom(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
//        if (!(body instanceof Result || body instanceof String)) {
//            body = Result.ok(body);
//        }
        if (returnType.hasMethodAnnotation(MethodDesc.class)) {
            MethodDesc methodDesc = returnType.getMethodAnnotation(MethodDesc.class);
            if (methodDesc != null) {
                logger.info("{}返回:\n{}", methodDesc.value(), body);
            }
        }
        return body;
    }

}
