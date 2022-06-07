package com.example.shiroAuthencation.openfeign;

import com.example.core.util.Constants;
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
        name = Constants.APPLICATION_NAME_SYSTEM,
        path = Constants.CONTEXT_PATH_SYSTEM + Constants.OPENFEIGN_CALL_PREFIX + "/user",
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
    UserVo findByLoginName(@RequestParam(value = "username") String loginName);

}
