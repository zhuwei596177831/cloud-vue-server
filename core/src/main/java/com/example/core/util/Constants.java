package com.example.core.util;


/**
 * @author 朱伟伟
 * @date 2021-05-18 16:58:40
 * @description
 */
public class Constants {

    public static final String GATEWAY_TOKEN = "gateway_token";
    public static final String GATEWAY_SIGN_KEY = "gateway_sign_key";
    public static final String GATEWAY_TIME = "gateway_time";
    public static final String GATEWAY_NONCE = "gateway_nonce";
    public static final String TERMS_OF_SERVICE_URL = "http://127.0.0.1:9000";
    public static final String USER_LOGIN_ERROR = "用户名或密码错误";
    public static final String USER_ACCOUNT_LOCKED = "账户已锁定";
    public static final String DEFAULT_PASSWORD = "000000";
    public static final String SUCCESS_CODE_STRING = "0000";
    public static final String SUCCESS_MSG_STRING = "成功";
    public static final String X_TOKEN_NAME = "X-Token";

    /**
     * 是否为系统默认（是）
     */
    public static final String YES = "Y";

    /**
     * 校验返回结果码
     */
    public final static String UNIQUE = "0";
    public final static String NOT_UNIQUE = "1";

    /**
     * Spring Cache Manager 名称
     */
    public static final String ehCacheCacheManagerName = "ehCacheCacheManager";
    public static final String redisCacheManagerName = "redisCacheManager";
    public static final String caffeineCacheManagerName = "caffeineCacheManager";
    public static final String multiCacheManagerName = "multiCacheManager";

    /**
     * 各个服务的application name
     */
    public static final String APPLICATION_NAME_SYSTEM = "system";
    public static final String APPLICATION_NAME_ACCOUNT = "account";
    public static final String APPLICATION_NAME_BUSINESS = "business";
    public static final String APPLICATION_NAME_ORDER = "order";
    public static final String APPLICATION_NAME_STORAGE = "storage";

    /**
     * 日期相关format
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    private Constants() {

    }
}
