package com.example.test.controller.business;

import com.example.core.entity.Result;
import com.example.shiroAuthencation.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2021-07-07 14:28:54
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @GetMapping("/test")
    public Result test() {
        System.out.println(getUser());
        return Result.ok();
    }

}
