package com.example.system.responseCode;

import com.example.core.responsecode.ResponseCode;

/**
 * @author 朱伟伟
 * @date 2022-05-07 17:52:09
 * @description 用户异常消息码 枚举
 */
public enum UserResponseCode implements ResponseCode {

    LOGIN_NAME_EXIST("login_name_exist", "登录名已存在"),
    PHONE_EXIST("phone_exist", "手机号已存在"),
    USER_NOT_EXIST("user_not_exist", "用户不存在"),
    USER_ID_NOT_NULL("user_id_not_null", "用户id不可为空"),
    USER_PASSWORD_NOT_NULL("user_password_not_null", "用户密码不可为空"),
    ;

    private final String code;
    private final String msg;

    UserResponseCode(String code, String msg) {
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
