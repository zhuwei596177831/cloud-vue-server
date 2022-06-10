package com.example.coreweb.rescode.system;

import com.example.core.rescode.ResponseCode;

/**
 * @author 朱伟伟
 * @date 2022-06-06 13:40:35
 * @description 修改用户密码异常消息码 枚举
 */
public enum PwdResponseCode implements ResponseCode {

    PASSWORD_OLD_NEW_NOT_EQUALS("password_old_new_not_equals", "两次输入的密码不一致"),
    PASSWORD_NOT_CORRECT("password_not_correct", "密码输入不正确"),
    NOT_USE_OLD_PASSWORD("not_use_old_password", "不能使用旧密码"),

    ;

    private final String code;
    private final String msg;

    PwdResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
