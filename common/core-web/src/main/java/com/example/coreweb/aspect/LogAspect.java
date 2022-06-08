package com.example.coreweb.aspect;

import com.alibaba.fastjson.JSON;
import com.example.core.entity.Json;
import com.example.core.util.Constants;
import com.example.core.util.StringUtils;
import com.example.core.vo.system.IpAddressVo;
import com.example.core.vo.system.LoginLogVo;
import com.example.core.vo.system.OpeLogVo;
import com.example.core.vo.system.UserVo;
import com.example.coreweb.annotation.Log;
import com.example.coreweb.enums.BusinessStatus;
import com.example.coreweb.enums.LogType;
import com.example.coreweb.openfeign.LogFeign;
import com.example.coreweb.util.AddressUtils;
import com.example.coreweb.util.IpUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

/**
 * 日志记录处理切面
 *
 * @author: 朱伟伟
 * @date: 2022-06-07 15:11
 **/
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private LogFeign logFeign;
    @Autowired
    private HttpServletRequest request;

    /**
     * 正常返回通知
     *
     * @param joinPoint: 连接点
     * @param log:       日志注解
     * @param result:    目标方法执行的结果
     * @author: 朱伟伟
     * @date: 2022-06-07 15:13
     **/
    @AfterReturning(pointcut = "@annotation(log)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Log log, Object result) {
        if (logFeign != null) {
            LogType type = log.type();
            if (type == LogType.LOGIN) {
                handleLoginLog(joinPoint, log, null, result);
            } else {
                handleOpeLog(joinPoint, log, null, result);
            }
        }
    }

    /**
     * 异常通知
     *
     * @param joinPoint: 连接点
     * @param log:       日志注解
     * @param e:         异常
     * @author: 朱伟伟
     * @date: 2022-06-07 15:14
     **/
    @AfterThrowing(value = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, Exception e) {
        if (logFeign != null) {
            LogType type = log.type();
            if (type == LogType.LOGIN) {
                handleLoginLog(joinPoint, log, e, null);
            } else {
                handleOpeLog(joinPoint, log, e, null);
            }
        }
    }

    /**
     * 处理登录日志
     *
     * @param joinPoint: 连接点
     * @param log:       日志注解
     * @param e:         异常
     * @param result:    目标方法执行的结果
     * @author: 朱伟伟
     * @date: 2022-06-07 15:39
     **/
    public void handleLoginLog(final JoinPoint joinPoint, final Log log, final Exception e, final Object result) {
        try {
            LoginLogVo loginLogVo = new LoginLogVo();
            loginLogVo.setAccessTime(LocalDateTime.now());
            String ipAddr = IpUtils.getIpAddr(request);
            loginLogVo.setIpAddr(ipAddr);
            if (StringUtils.isNotEmpty(ipAddr)) {
                IpAddressVo ipAddressVo = AddressUtils.getIpAddressVo(ipAddr);
                if (ipAddressVo != null) {
                    loginLogVo.setIpLocation(ipAddressVo.getProvince() + ipAddressVo.getCity());
                }
            }
            loginLogVo.setLoginName(request.getParameter("username"));
            loginLogVo.setStatus(Constants.LOGIN_SUCCESS_STATUS);
            loginLogVo.setMsg(Constants.SUCCESS_MSG_LOGIN);
            if (e != null) {
                loginLogVo.setStatus(Constants.LOGIN_FAIL_STATUS);
                loginLogVo.setMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            } else {
                if (result instanceof Json) {
                    Json json = (Json) result;
                    if (json.notSuccess()) {
                        loginLogVo.setStatus(Constants.LOGIN_FAIL_STATUS);
                        loginLogVo.setMsg(json.getMsg());
                    }
                }
            }
            logFeign.loginLog(loginLogVo);
        } catch (Exception exp) {
            exp.printStackTrace();
            // 记录本地异常日志
            logger.error("==记录登录日志异常==");
            logger.error("异常信息:{}", exp.getMessage());
        }
    }

    /**
     * 处理操作日志
     *
     * @param joinPoint: 连接点
     * @param log:       日志注解
     * @param e:         异常
     * @param result:    目标方法执行的结果
     * @author: 朱伟伟
     * @date: 2022-06-07 15:39
     **/
    public void handleOpeLog(final JoinPoint joinPoint, final Log log, final Exception e, final Object result) {
        try {
            OpeLogVo opeLogVo = new OpeLogVo();
            opeLogVo.setStatus(BusinessStatus.SUCCESS.ordinal());
            opeLogVo.setErrorMsg(Constants.SUCCESS_MSG_STRING);
            String ipAddr = IpUtils.getIpAddr(request);
            opeLogVo.setOpeIp(ipAddr);
            if (StringUtils.isNotEmpty(ipAddr)) {
                IpAddressVo ipAddressVo = AddressUtils.getIpAddressVo(ipAddr);
                if (ipAddressVo != null) {
                    opeLogVo.setOpeLocation(ipAddressVo.getProvince() + ipAddressVo.getCity());
                }
            }
            opeLogVo.setOpeUrl(request.getRequestURI());
            opeLogVo.setOpeTime(LocalDateTime.now());
            Object principal = SecurityUtils.getSubject().getPrincipal();
            if (principal != null) {
                UserVo userVo = (UserVo) principal;
                opeLogVo.setOpeName(userVo.getName());
            }
            if (e != null) {
                opeLogVo.setStatus(BusinessStatus.FAIL.ordinal());
                opeLogVo.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            } else {
                if (result instanceof Json) {
                    Json json = (Json) result;
                    if (json.notSuccess()) {
                        opeLogVo.setStatus(BusinessStatus.FAIL.ordinal());
                        opeLogVo.setErrorMsg(json.getMsg());
                    }
                }
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            opeLogVo.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            opeLogVo.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            // 设置action动作
            opeLogVo.setBusinessType(log.businessType().ordinal());
            // 设置标题
            opeLogVo.setTitle(log.title());
            // 设置操作人类别
            opeLogVo.setOperatorType(log.operatorType().ordinal());
            // 是否需要保存request，参数和值
            if (log.isSaveRequestData()) {
                // 获取参数的信息，传入到数据库中。
                String requestMethod = opeLogVo.getRequestMethod();
                if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
                    String params = argsArrayToString(joinPoint.getArgs());
                    opeLogVo.setOpeParam(StringUtils.substring(params, 0, 2000));
                }
            }
            // 是否需要保存response，参数和值
            if (log.isSaveResponseData() && StringUtils.isNotNull(result)) {
                opeLogVo.setJsonResult(StringUtils.substring(JSON.toJSONString(result), 0, 2000));
            }
            // 保存数据库
            logFeign.opeLog(opeLogVo);
        } catch (Exception exp) {
            exp.printStackTrace();
            // 记录本地异常日志
            logger.error("==记录操作日志异常==");
            logger.error("异常信息:{}", exp.getMessage());
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params.append(jsonObj.toString()).append(" ");
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
