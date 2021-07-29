package com.example.shiroAuthencation.controller;

import com.example.core.entity.PageInfo;
import com.example.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 朱伟伟
 * @date 2021-06-24 17:20:46
 * @description
 */
public abstract class BaseController {

    /**
     * 获取用户信息
     *
     * @author: 朱伟伟
     * @date: 2021-06-24 17:21
     **/
    protected User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取分页参数
     *
     * @author: 朱伟伟
     * @date: 2021-06-24 20:47
     * @see org.springframework.web.filter.RequestContextFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     **/
    protected PageInfo getPageInfo() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String pageNum = request.getHeader("pageNum");
        pageNum = StringUtils.hasText(pageNum) ? pageNum : "1";
        String pageSize = request.getHeader("pageSize");
        pageSize = StringUtils.hasText(pageSize) ? pageSize : "10";
        return new PageInfo(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }

}
