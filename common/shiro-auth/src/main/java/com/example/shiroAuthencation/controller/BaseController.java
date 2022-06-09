package com.example.shiroAuthencation.controller;

import com.example.core.entity.PageInfo;
import com.example.core.entity.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朱伟伟
 * @date 2021-06-24 17:20:46
 * @description
 */
public abstract class BaseController {

    protected static final int DEFAULT_PAGE_NUM = 1;
    protected static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 获取用户信息
     *
     * @author: 朱伟伟
     * @date: 2021-06-24 17:21
     **/
    protected ShiroUser getUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取分页参数
     *
     * @author: 朱伟伟
     * @date: 2021-06-24 20:47
     **/
    protected PageInfo getPageInfo() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String pageNumStr = request.getParameter("pageNum");
        int pageNum = StringUtils.hasText(pageNumStr) ? Integer.parseInt(pageNumStr) : DEFAULT_PAGE_NUM;
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = StringUtils.hasText(pageSizeStr) ? Integer.parseInt(pageSizeStr) : DEFAULT_PAGE_SIZE;
        return new PageInfo(pageNum, pageSize);
    }

}
