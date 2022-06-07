package com.example.shiroAuthencation.filter;

import com.example.core.entity.Json;
import com.example.core.responsecode.ApplicationResponseCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author 朱伟伟
 * @date 2021-05-24 16:03:06
 * @description 权限认证filter
 */
public class CustomAccessFilter extends AccessControlFilter implements OrderedFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        writeJson(ApplicationResponseCode.UNAUTHORIZED.getJson(), response);
        return false;
    }

    /**
     * 回写json数据
     *
     * @param json:
     * @param response:
     * @author: 朱伟伟
     * @date: 2022-05-13 14:57
     **/
    private void writeJson(Json json, ServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        PrintWriter writer = response.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();
    }

    @Override
    public int getOrder() {
        //在MyHttpServletRequestWrapperFilter之前执行
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}
