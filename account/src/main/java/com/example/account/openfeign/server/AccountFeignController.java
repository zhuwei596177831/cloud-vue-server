package com.example.account.openfeign.server;

import com.alibaba.fastjson.JSON;
import com.example.account.service.AccountService;
import com.example.core.entity.Result;
import com.example.core.vo.system.UserVo;
import org.springframework.web.bind.annotation.*;

/**
 * @author 朱伟伟
 * @date 2021-12-01 16:00:03
 * @description
 */
@RestController
@RequestMapping("/openFeign/account")
public class AccountFeignController {

    private final AccountService accountService;

    public AccountFeignController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/saveAccount")
    public Result saveAccount(@RequestHeader("header") String header,
                              @RequestParam("name") String name,
                              @RequestBody UserVo userVo) {
        System.out.println(header);
        System.out.println(name);
        System.out.println(JSON.toJSONString(userVo));
        return accountService.save();
    }

}
