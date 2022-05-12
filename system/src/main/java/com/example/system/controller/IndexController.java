package com.example.system.controller;

import com.example.core.entity.Json;
import com.example.shiroAuthencation.controller.BaseController;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朱伟伟
 * @date 2021-05-28 09:58:19
 * @description 首页控制器
 */
@Api(tags = "首页")
@ApiSupport(author = "朱伟伟")
@RestController
public class IndexController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    /**
     * @author: 朱伟伟
     * @date: 2021-05-22 23:28
     * @description: 首页
     **/
    @ApiOperation(value = "首页")
    @PostMapping("/index")
    public Json index() {
        return Json.success();
    }

}
