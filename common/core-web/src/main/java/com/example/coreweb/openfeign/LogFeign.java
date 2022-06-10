package com.example.coreweb.openfeign;

import com.example.core.entity.Json;
import com.example.core.util.Constants;
import com.example.core.vo.system.LoginLogVo;
import com.example.core.vo.system.OpeLogVo;
import com.example.coreweb.openfeign.fallback.LogFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 朱伟伟
 * @date 2022-06-07 14:25:12
 * @description 日志记录 openfeign client
 */
@FeignClient(
        value = Constants.APPLICATION_NAME_SYSTEM,
        path = Constants.CONTEXT_PATH_SYSTEM + "/log",
        contextId = "LogFeign",
        fallbackFactory = LogFeignFallBack.class
)
public interface LogFeign {

    /**
     * 记录登录日志
     *
     * @param loginLogVo:
     * @author: 朱伟伟
     * @date: 2022-06-07 14:48
     **/
    @PostMapping("/loginLog")
    Json loginLog(@RequestBody LoginLogVo loginLogVo);

    /**
     * 记录操作日志
     *
     * @param opeLogVo:
     * @author: 朱伟伟
     * @date: 2022-06-07 15:07
     **/
    @PostMapping("/opeLog")
    Json opeLog(@RequestBody OpeLogVo opeLogVo);

}
