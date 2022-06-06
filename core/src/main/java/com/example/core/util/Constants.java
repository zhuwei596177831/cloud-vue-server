package com.example.core.util;


/**
 * @author 朱伟伟
 * @date 2021-05-18 16:58:40
 * @description
 */
public abstract class Constants {

    public static final String GATEWAY_TOKEN = "gateway_token";
    public static final String GATEWAY_SIGN_KEY = "gateway_sign_key";
    public static final String GATEWAY_TIME = "gateway_time";
    public static final String GATEWAY_NONCE = "gateway_nonce";
    public static final String TERMS_OF_SERVICE_URL = "http://127.0.0.1:9000";
    public static final String USER_LOGIN_ERROR = "用户名或密码错误";
    public static final String USER_ACCOUNT_LOCKED = "账户已锁定";
    public static final String DEFAULT_PASSWORD = "123456";
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

    /**
     * 登录密码使用RSA加密传输的私钥
     */
    public static final String PWD_RSA_PRIVATE_KEY =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAII6KCj/Km4K/HrvMFrCYndSRsCK81+7+NMjHKHMe6XF4S4ifOuKDy"
                    + "/CwjZ4qOb8i1Bzp8o2d0KBaXvfI+vTIULPBDYTPgDfs7lWxtYFKXIHj9Q7MCdNMq60iAU8ZfiNVkGb5cfjeNCz8fVjybV0XZwY4JZ2xsf24K2gOS"
                    + "+I6mklAgMBAAECgYBJoprMu6OBP6MN3CuqVhmZQKOTAb6llkMXF9Z7woTz8lTmYkfiY1k891IbvNQ8b/ZSUmNA2tVKfKRpj33fPa2jJb5A/w"
                    + "/WZyomXEL3PS3vYTsmlPnbODM9Jeis2GV26/Xva9NYXBgJGMv0NCFwwvdIJI5znTn5b97NmmU+ngEZDQJBAOf8CfKF640"
                    + "/3t5WI3XLYGp2VoPMHXobENn6ZkQn7j+rAJz1alNhKmL9aRfnLiJ/npSMT0i+n5wXLXmWVHulsq8CQQCPtWKo2QoaX88RqBkx/nYX6UeN"
                    + "+sMFcYoL8te9zZePlraDony5GqWEGpI5H9xrh/X12xByoIIYHt6rKJ7buWZrAkEAx2pQ4rlcEr"
                    + "+kxVsmgEujReET3ZfIfv5lLfuhjVUF6JUGoeYU0DfmR9GuVW3UqrMpjmhOfynd6j/CR5KCbQey4QJAFLwnMBWgz41jYzjN6di9UVXnbSTxXqezM2Ymmsw"
                    + "/QgiM7RFUzaB6oUT0NCnUs+86He8twFxIaRKLLkb+JNXrYQJAMmjEahfpYcgzZ/f8Wwrr5e3qQCyFtqWNHrI6pP+0a8w8XC0NEdLInWE3e0xYJ"
                    + "+wmtar8AOOjPqrRZNlyNkqz3w==";

}
