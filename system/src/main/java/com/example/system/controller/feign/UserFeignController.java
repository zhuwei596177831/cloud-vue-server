package com.example.system.controller.feign;

import com.example.core.entity.Result;
import com.example.system.entity.User;
import com.example.system.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 朱伟伟
 * @date 2021-05-22 18:45:37
 * @description User Feign controller
 */
@RestController
@RequestMapping("/openFeign/user")
public class UserFeignController {

    @Autowired
    private UserService userService;

    /**
     * @param username:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:48
     * @description: 根据用户名查询用户
     **/
    @PostMapping("/getUserByLoginName")
    public User getUserByLoginName(@RequestParam String username) {
        return userService.getUserByLoginName(username);
    }

}
