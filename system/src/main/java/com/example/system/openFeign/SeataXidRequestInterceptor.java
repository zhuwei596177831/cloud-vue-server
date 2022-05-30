package com.example.system.openFeign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Component;

/**
 * @author 朱伟伟
 * @date 2021-12-04 21:54:36
 * @description openfeign 传递塞seata 分布式 xid
 */
@Component
public class SeataXidRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        //解决不传递请求头中的token
        //ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //if (attributes != null) {
        //    HttpServletRequest request = attributes.getRequest();
        //    Enumeration<String> headerNames = request.getHeaderNames();
        //    //可以在这里将自定义请求头传递进去， key 请求， value 值
        //    //处理上游请求头信息，传递时继续携带
        //    while (headerNames.hasMoreElements()) {
        //        String name = headerNames.nextElement();
        //        String values = request.getHeader(name);
        //        template.header(name, values);
        //    }
        //}
        //template.header(MediaType.)
        // 解决seata的xid未传递
        template.header(RootContext.KEY_XID, RootContext.getXID());
    }
}
