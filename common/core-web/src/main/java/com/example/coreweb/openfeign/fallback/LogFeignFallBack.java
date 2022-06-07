package com.example.coreweb.openfeign.fallback;

import com.alibaba.fastjson.JSON;
import com.example.core.entity.Json;
import com.example.core.vo.system.LoginLogVo;
import com.example.core.vo.system.OpeLogVo;
import com.example.coreweb.openfeign.LogFeign;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2022-06-07 14:44:25
 * @description 日志记录 openfeign client fallback
 */
@Component
public class LogFeignFallBack implements FallbackFactory<LogFeign> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public LogFeign create(Throwable cause) {
        return new LogFeign() {
            @Override
            public Json loginLog(LoginLogVo loginLogVo) {
                cause.printStackTrace();
                logger.error("记录登录日志：{}，异常：{}", JSON.toJSONString(loginLogVo), cause.getMessage());
                return Json.fail(cause.getMessage());
            }

            @Override
            public Json opeLog(OpeLogVo opeLogVo) {
                cause.printStackTrace();
                logger.error("记录操作日志：{}，异常：{}", JSON.toJSONString(opeLogVo), cause.getMessage());
                return Json.fail(cause.getMessage());
            }
        };
    }
}
