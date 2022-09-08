package com.example.coreweb.filter;

import com.example.core.uuid.UUID;
import com.example.coreweb.MyThreadLocalHolder;
import com.example.coreweb.MyTransmittableThreadLocal;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author 朱伟伟
 * @date 2022-09-08 14:56:20
 * @description
 */
@Component
public class ThreadLocalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            MyThreadLocalHolder.bind(UUID.randomUUID().toString() + "_thread_local");
            MyTransmittableThreadLocal.bind(UUID.randomUUID().toString() + "_transmittable_thread_local");
            chain.doFilter(request, response);
        } finally {
            MyThreadLocalHolder.unbind();
            MyTransmittableThreadLocal.unbind();
        }
    }

    @Override
    public void destroy() {

    }
}
