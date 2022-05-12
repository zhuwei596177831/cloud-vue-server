package com.example.coreweb.exception;

import com.example.core.entity.Json;

/**
 * @author 朱伟伟
 * @date 2022-05-07 17:52:09
 * @description 应用层异常消息码 枚举
 */
public enum ApplicationResponseCode implements ResponseCode {

    RECORD_NOT_EXIST("record_not_exist", "记录不存在"),
    UNKNOWN_ERROR("unknown_error", "未知异常"),
    GATEWAY_CHECK_FAIL("9999", "网关校验失败"),
    UNAUTHORIZED("401", "认证失败,请登录");;

    private final String code;
    private final String msg;

    ApplicationResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public Json getJson() {
        return new Json(this.code, this.msg, false);
    }
}
