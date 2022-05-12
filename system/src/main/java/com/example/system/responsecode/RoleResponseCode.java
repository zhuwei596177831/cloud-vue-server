package com.example.system.responsecode;

import com.example.coreweb.exception.ResponseCode;

/**
 * @author 朱伟伟
 * @date 2022-05-07 17:52:09
 * @description 角色异常消息码 枚举
 */
public enum RoleResponseCode implements ResponseCode {

    CODE_EXIST("code_exist", "角色编码已存在"),
    NAME_EXIST("name_exist", "角色名称已存在"),
    ALREADY_BIND_USER("already_bind_user", "角色已和用户绑定"),
    ;

    private final String code;
    private final String msg;

    RoleResponseCode(String code, String msg) {
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
