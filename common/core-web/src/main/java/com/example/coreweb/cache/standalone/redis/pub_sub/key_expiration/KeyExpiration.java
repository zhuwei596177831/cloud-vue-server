package com.example.coreweb.cache.standalone.redis.pub_sub.key_expiration;

/**
 * @author 朱伟伟
 * @date 2023-06-13 10:51:38
 */
public enum KeyExpiration {
    test("test", "key失效测试"),
    ;

    private final String key;
    private final String desc;

    KeyExpiration(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
