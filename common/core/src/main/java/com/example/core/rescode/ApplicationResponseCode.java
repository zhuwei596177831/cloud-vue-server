package com.example.core.rescode;

/**
 * @author 朱伟伟
 * @date 2022-05-07 17:52:09
 * @description 应用层异常消息码 枚举
 */
public enum ApplicationResponseCode implements ResponseCode {

    GATEWAY_CHECK_FAIL("9999" , "网关校验失败"),
    UNAUTHORIZED("401" , "认证失败,请重新登录"),
    RECORD_NOT_EXIST("record_not_exist" , "记录不存在"),
    PRIMARY_KEY_IS_NULL("primary_key_is_null" , "主键不可为空"),
    UNKNOWN_ERROR("unknown_error" , "未知异常"),
    TOKEN_GENERATE_ERROR("token_generate_error" , "生成token异常"),
    ;

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

}
