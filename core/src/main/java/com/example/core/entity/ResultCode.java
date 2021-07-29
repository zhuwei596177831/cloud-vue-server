package com.example.core.entity;


import java.text.MessageFormat;

/**
 * @author 朱伟伟
 * @description
 **/
public enum ResultCode {
    SUCCESS("0000", "成功"),

    FAILED("0001", "失败"),

    ERROR("0002", "未知错误"),

    COMMON("8888", "{0}"),

    GATEWAY_CHECK_FAIL("9999", "网关校验失败"),

    UNAUTHORIZED("401", "认证失败,请登录");

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public <T> Result<T> getResult() {
        return new Result<>(this.code, this.msg, null);
    }

    public <T> Result<T> getResult(Object... errMsg) {
        String format = MessageFormat.format(this.msg, errMsg);
        return new Result<>(this.code, format, null);
    }

}
