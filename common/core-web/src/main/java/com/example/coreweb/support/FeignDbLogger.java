package com.example.coreweb.support;

import com.example.coreweb.openfeign.LogFeign;
import feign.Response;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * @author 朱伟伟
 * @date 2022-08-19 15:32:27
 * @description 自定义Logger之feign调用打印
 */
public class FeignDbLogger extends feign.Logger {

    //当前FeignClient对应的ApplicationContext
    private final ApplicationContext applicationContext;
    private final LogFeign logFeign;

    public FeignDbLogger(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.logFeign = this.applicationContext.getBean(LogFeign.class);
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
    }
}
