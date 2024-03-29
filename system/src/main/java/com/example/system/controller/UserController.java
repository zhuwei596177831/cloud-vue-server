package com.example.system.controller;

import com.example.api.system.entity.User;
import com.example.core.entity.GenericJson;
import com.example.core.entity.Json;
import com.example.core.entity.PageInfo;
import com.example.core.entity.ShiroUser;
import com.example.core.vo.system.UserInfoVo;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-05-15 17:24:42
 * @description 用户controller
 */
@Api(tags = "用户")
@ApiSupport(author = "朱伟伟")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户分页数据
     *
     * @param user:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:20
     **/
    @ApiOperation(value = "用户分页数据")
    @PostMapping("/userPageList")
    public Json userPageList(User user) {
        PageInfo pageInfo = getPageInfo();
        List<User> userList = userService.userList(user, pageInfo);
        return Json.ok(userList);
    }

    /**
     * 添加用户
     *
     * @param user:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:26
     **/
    @ApiOperation(value = "添加用户")
    @PostMapping("/addUser")
    public Json addUser(@RequestBody @Validated({User.Add.class}) User user) {
        ShiroUser shiroUser = getUser();
        user.setInputUserId(shiroUser.getId());
        user.setInputTime(LocalDateTime.now());
        return userService.addUser(user);
    }

    /**
     * 修改用户
     *
     * @param user:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:26
     **/
    @ApiOperation(value = "修改用户")
    @PutMapping("/updateUser")
    public Json updateUser(@RequestBody @Validated({User.Update.class}) User user) {
        ShiroUser shiroUser = getUser();
        user.setUpdateUserId(shiroUser.getId());
        user.setUpdateTime(LocalDateTime.now());
        return userService.updateUser(user);
    }

    /**
     * 删除用户
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:50
     **/
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @DeleteMapping("/deleteUserById/{userId}")
    public Json deleteUserById(@PathVariable @NotNull(message = "用户id不能为空") Long userId) {
        return userService.deleteUserById(userId);
    }

    /**
     * 重置密码
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:53
     **/
    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @PostMapping("/resetPassword/{userId}")
    public Json resetPassword(@PathVariable @NotNull(message = "用户id不能为空") Long userId) {
        return userService.resetPassword(userId);
    }

    /**
     * 获取用户信息 用于前端vuex存储
     *
     * @author: 朱伟伟
     * @date: 2021-07-28 16:29
     **/
    @ApiOperation(value = "获取用户信息 用于前端vuex存储")
    @PostMapping("/findUserInfo")
    public Json findUserInfo() {
        UserInfoVo userInfoVo = userService.findUserInfo(getUser());
        return Json.ok(userInfoVo);
    }

    /**
     * 用户密码重置
     *
     * @author: 朱伟伟
     * @date: 2022-06-06 14:46
     **/
    @ApiOperation(value = "用户密码重置")
    @PostMapping("/resetPwd")
    public Json resetPwd(@RequestBody ShiroUser shiroUser) {
        return userService.resetPwd(shiroUser);
    }

    /**
     * 更新用户头像
     *
     * @param url: 上传后的图片地址
     * @author: 朱伟伟
     * @date: 2022-06-06 15:55
     **/
    @ApiOperation(value = "更新用户头像")
    @PostMapping("/updateHeadImageUrl")
    public Json updateHeadImageUrl(@RequestParam String url) {
        return userService.updateHeadImageUrl(url, getUser());
    }

    /**
     * @param loginName:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:48
     * @description: 根据登录名查询用户
     **/
    @PostMapping("/findByLoginName")
    public GenericJson<User> findByLoginName(@RequestParam String loginName) {
        return GenericJson.ok(userService.findByLoginName(loginName));
    }

}
