package com.example.system.controller;

import com.example.core.entity.Json;
import com.example.core.util.Constants;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.service.MenuService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * @author 朱伟伟
 * @date 2021-05-28 09:56:59
 * @description 登录接口
 */
@Api(tags = "登录")
@ApiSupport(author = "朱伟伟")
@RestController
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    private DefaultSessionManager defaultSessionManager;
    @Autowired
    private MenuService menuService;


    /**
     * @param username:
     * @param password:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:55
     * @description: 登录
     **/
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @PostMapping("/login")
    public Json login(@RequestParam @NotEmpty(message = "用户名不能为空") String username,
                      @RequestParam @NotEmpty(message = "密码不能为空") String password) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (IncorrectCredentialsException | UnknownAccountException e) {
            e.printStackTrace();
            return Json.fail(Constants.USER_LOGIN_ERROR);
        } catch (LockedAccountException e) {
            e.printStackTrace();
            return Json.fail(Constants.USER_ACCOUNT_LOCKED);
        } catch (Exception e) {
            e.printStackTrace();
            return Json.fail(e.getMessage());
        }
//        long globalSessionTimeout = defaultSessionManager.getGlobalSessionTimeout();
//        Serializable sessionId = subject.getSession(false).getId();
//        Map<String, Object> map = new HashMap<>();
//        map.put(ConstantsHolder.SHIRO_COOKIE_NAME, sessionId);
//        map.put("tokenExpireTime", globalSessionTimeout / 1000);
//        return Result.ok(map);
        return Json.ok(getUser().getName());
    }

    /**
     * 登出
     *
     * @author: 朱伟伟
     * @date: 2021-06-26 22:52
     **/
    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public Json logout() {
        SecurityUtils.getSubject().logout();
        return Json.success();
    }

}
