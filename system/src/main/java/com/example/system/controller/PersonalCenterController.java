package com.example.system.controller;

import com.example.core.entity.Json;
import com.example.core.vo.system.UserProfile;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朱伟伟
 * @date 2022-06-06 09:20:10
 * @description 个人中心
 */
@Api(tags = "个人中心")
@ApiSupport(author = "朱伟伟")
@RestController
@RequestMapping("/personalCenter")
public class PersonalCenterController extends BaseController {

    private final UserService userService;

    public PersonalCenterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 更新基本资料
     *
     * @param userProfile:
     * @author: 朱伟伟
     * @date: 2022-06-06 09:22
     **/
    @ApiOperation(value = "更新基本资料")
    @PostMapping("/updateProfile")
    public Json updateProfile(@RequestBody @Validated({UserProfile.UpdateProfile.class}) UserProfile userProfile,
                              HttpServletRequest request) {
        return userService.updateProfile(userProfile, getUser(), request);
    }

    /**
     * 修改用户密码
     *
     * @param userProfile:
     * @author: 朱伟伟
     * @date: 2022-06-06 09:22
     **/
    @ApiOperation(value = "修改用户密码")
    @PostMapping("/updatePwd")
    public Json updatePwd(@RequestBody @Validated({UserProfile.UpdatePwd.class}) UserProfile userProfile) {
        return userService.updatePwd(userProfile, getUser());
    }

}
