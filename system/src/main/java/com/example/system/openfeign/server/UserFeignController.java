package com.example.system.openfeign.server;

import com.example.api.system.entity.User;
import com.example.core.util.Constants;
import com.example.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2021-05-22 18:45:37
 * @description User Feign controller
 */
@RestController
@RequestMapping(Constants.OPENFEIGN_CALL_PREFIX + "/user")
public class UserFeignController {

    @Autowired
    private UserService userService;

    /**
     * @param username:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:48
     * @description: 根据用户名查询用户
     **/
    @PostMapping("/findByLoginName")
    public User findByLoginName(@RequestParam String username) {
        User user = userService.findByLoginName(username);
        if (user == null) {
            return null;
        }
        return user;
    }

}
