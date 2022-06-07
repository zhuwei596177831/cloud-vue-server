package com.example.coreweb.error;


import com.example.core.entity.Json;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朱伟伟
 * @date 2021-01-21 16:09:54
 * @description 自定义Error controller处理未映射到的RequestMapping
 * @see org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements ErrorController {

    @RequestMapping
    public Json error(HttpServletRequest request) {
        return Json.fail("无效的请求");
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
