package com.example.coreweb.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.core.enums.GatewayWhiteUrl;
import com.example.core.util.ConstantsHolder;
import com.example.coreweb.exception.ApplicationResponseCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.DigestUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        String GATEWAY_SIGN_KEY;
        try {
            GATEWAY_SIGN_KEY = stringRedisTemplate.boundValueOps(ConstantsHolder.GATEWAY_SIGN_KEY).get();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("get gateway-signKey token error", e);
            servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
            servletResponse.getWriter().write(getTokenErrorData());
            return;
        }
        String GATEWAY_TIME = request.getHeader(ConstantsHolder.GATEWAY_TIME);
        String GATEWAY_NONCE = request.getHeader(ConstantsHolder.GATEWAY_NONCE);
        String GATEWAY_TOKEN = request.getHeader(ConstantsHolder.GATEWAY_TOKEN);
        if (GATEWAY_TIME == null || GATEWAY_SIGN_KEY == null || GATEWAY_NONCE == null ||
                !(DigestUtils.md5DigestAsHex((GATEWAY_TIME + GATEWAY_SIGN_KEY + GATEWAY_NONCE).intern().getBytes())).equals(GATEWAY_TOKEN)
        ) {
            servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            servletResponse.getWriter().write(ApplicationResponseCode.GATEWAY_CHECK_FAIL.getJson().toString());
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * @param servletPath:
     * @author: 朱伟伟
     * @date: 2021-06-01 15:50
     * @description: 过滤feign rest调用
     **/
    private boolean isWhitelistUrl(String servletPath) {
        return Arrays.stream(GatewayWhiteUrl.values()).anyMatch(i -> antPathMatcher.match(i.getUrl(), servletPath));
    }

    public String getTokenErrorData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        jsonObject.put("msg", "get gateway-signKey token error");
        return jsonObject.toJSONString();
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getOrder() {
        return OrderedFilter.HIGHEST_PRECEDENCE;
    }
}
