package com.example.shiroAuthencation.openfeign;

import com.example.core.vo.system.UserVo;
import com.example.shiroAuthencation.openfeign.fallback.UserFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 朱伟伟
 * @date 2021-05-19 10:29:09
 * @description User Feign
 */
@FeignClient(
        name = "system",
        path = "/system/openFeign/user",
        //相同的name或者url  不的path、configuration时 使用contextId加以区分
        contextId = "UserFeign",
        fallbackFactory = UserFeignFallBack.class
)
public interface UserFeign {

    /**
     * @param loginName:
     * @author: 朱伟伟
     * @date: 2021-05-22 19:01
     * @description: 根据用户名查询用户
     **/
    @PostMapping("/getUserByLoginName")
    UserVo getUserByLoginName(@RequestParam(value = "username") String loginName);

}
