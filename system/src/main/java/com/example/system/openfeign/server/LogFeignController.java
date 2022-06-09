package com.example.system.openfeign.server;

import com.example.core.entity.Json;
import com.example.core.util.Constants;
import com.example.core.vo.system.LoginLogVo;
import com.example.core.vo.system.OpeLogVo;
import com.example.system.service.LoginLogService;
import com.example.system.service.OpeLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2022-06-07 14:51:33
 * @description 日志记录
 */
@RestController
@RequestMapping(Constants.OPENFEIGN_CALL_PREFIX + "/log")
public class LogFeignController {

    private final LoginLogService loginLogService;
    private final OpeLogService opeLogService;

    public LogFeignController(LoginLogService loginLogService, OpeLogService opeLogService) {
        this.loginLogService = loginLogService;
        this.opeLogService = opeLogService;
    }

    /**
     * 记录登录日志
     *
     * @param loginLogVo:
     * @author: 朱伟伟
     * @date: 2022-06-07 14:54
     **/
    @PostMapping("/loginLog")
    public Json loginLog(@RequestBody LoginLogVo loginLogVo) {
        loginLogService.insertLog(loginLogVo);
        return Json.success();
    }

    /**
     * 记录操作日志
     *
     * @param opeLogVo:
     * @author: 朱伟伟
     * @date: 2022-06-07 15:09
     **/
    @PostMapping("/opeLog")
    public Json opeLog(@RequestBody OpeLogVo opeLogVo) {
        opeLogService.insertLog(opeLogVo);
        return Json.success();
    }

}
