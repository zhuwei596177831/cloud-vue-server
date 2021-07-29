package com.example.core.util;


/**
 * @author 朱伟伟
 * @date 2021-05-18 16:58:40
 * @description
 */
public class ConstantsHolder {

    public static final String GATEWAY_TOKEN = "gateway_token";
    public static final String GATEWAY_SIGN_KEY = "gateway_signkey";
    public static final String GATEWAY_TIME = "gateway_time";
    public static final String GATEWAY_NONCE = "gateway_nonce";
    public static final String TERMS_OF_SERVICE_URL = "http://127.0.0.1:8090";
    public static final String USER_LOGIN_ERROR = "用户名或密码错误";
    public static final String USER_TIME = "user_time";
    public static final String USER_SIGN = "user_sign";
    public static final String USER_NONCE = "user_nonce";
    public static final String SHIRO_COOKIE_NAME = "customSessionIdCookie";
    public static final String DEFAULT_PASSWORD = "000000";

    /**
     * 日期相关format
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";

    private ConstantsHolder() {

    }
}
