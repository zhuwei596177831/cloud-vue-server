package com.example.api.system.openfeign.client;

import com.example.api.system.entity.User;
import com.example.api.system.openfeign.fallback.UserFeignFallBack;
import com.example.core.entity.GenericJson;
import com.example.core.util.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 朱伟伟
 * @date 2021-05-19 10:29:09
 * @description User Feign
 */
@FeignClient(
        name = Constants.APPLICATION_NAME_SYSTEM,
        path = Constants.CONTEXT_PATH_SYSTEM + "/user",
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
    @PostMapping("/findByLoginName")
    GenericJson<User> findByLoginName(@RequestParam(value = "username") String loginName);

}
