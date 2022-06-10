package com.example.shiroAuthencation.filter;

import com.example.core.entity.Json;
import com.example.core.rescode.ApplicationResponseCode;
import com.example.core.util.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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
        boolean authenticated = SecurityUtils.getSubject().isAuthenticated();
        if (authenticated) {
            return true;
        }
        //判断是不是模块之间使用open feign调用
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String SHIRO_ANON_TIME = httpRequest.getHeader(Constants.SHIRO_ANON_TIME);
        String SHIRO_ANON_NONCE = httpRequest.getHeader(Constants.SHIRO_ANON_NONCE);
        String SHIRO_ANON_TOKEN = httpRequest.getHeader(Constants.SHIRO_ANON_TOKEN);
        if (StringUtils.isEmpty(SHIRO_ANON_TIME) || StringUtils.isEmpty(SHIRO_ANON_NONCE)
                || StringUtils.isEmpty(SHIRO_ANON_TOKEN)) {
            return false;
        }
        String sign = DigestUtils.md5DigestAsHex((SHIRO_ANON_TIME + Constants.SHIRO_ANON_SIGN_KEY + SHIRO_ANON_NONCE).getBytes());
        return sign.equals(SHIRO_ANON_TOKEN);
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
