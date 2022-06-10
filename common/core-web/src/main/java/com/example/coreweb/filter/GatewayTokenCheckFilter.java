package com.example.coreweb.filter;

import com.example.core.entity.Json;
import com.example.coreweb.enums.GatewayCheckWhiteListUrl;
import com.example.core.rescode.ApplicationResponseCode;
import com.example.core.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author 朱伟伟
 * @date 2021-05-18 17:54:19
 * @description 校验是否是网关路由过来的请求
 */
@Component
public class GatewayTokenCheckFilter implements Filter, OrderedFilter {

    private final Logger logger = LogManager.getLogger(getClass());

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (isWhitelistUrl(request.getServletPath())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String GATEWAY_TIME = request.getHeader(Constants.GATEWAY_TIME);
        String GATEWAY_NONCE = request.getHeader(Constants.GATEWAY_NONCE);
        String GATEWAY_TOKEN = request.getHeader(Constants.GATEWAY_TOKEN);
        if (StringUtils.isEmpty(GATEWAY_TIME) || StringUtils.isEmpty(GATEWAY_NONCE) || StringUtils.isEmpty(GATEWAY_TOKEN)) {
            writeJson(ApplicationResponseCode.GATEWAY_CHECK_FAIL.getJson(), servletResponse);
            return;
        }
        String sign = DigestUtils.md5DigestAsHex((GATEWAY_TIME + Constants.GATEWAY_SIGN_KEY + GATEWAY_NONCE).getBytes());
        if (!sign.equals(GATEWAY_TOKEN)) {
            writeJson(ApplicationResponseCode.GATEWAY_CHECK_FAIL.getJson(), servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 回写json数据
     *
     * @param json:
     * @param response:
     * @author: 朱伟伟
     * @date: 2022-05-13 14:42
     **/
    private void writeJson(Json json, ServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        PrintWriter writer = response.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();
    }

    /**
     * @param servletPath:
     * @author: 朱伟伟
     * @date: 2021-06-01 15:50
     * @description: 过滤feign rest调用
     **/
    private boolean isWhitelistUrl(String servletPath) {
        return Arrays.stream(GatewayCheckWhiteListUrl.values()).anyMatch(i -> antPathMatcher.match(i.getUrl(), servletPath));
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getOrder() {
        //最先执行
        return OrderedFilter.HIGHEST_PRECEDENCE;
    }
}
