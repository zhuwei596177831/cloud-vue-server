package com.example.system.responseCode;

import com.example.core.responsecode.ResponseCode;

/**
 * @author 朱伟伟
 * @date 2022-05-07 17:52:09
 * @description 菜单异常消息码 枚举
 */
public enum MenuResponseCode implements ResponseCode {

    PARENT_ID_IS_NULL("parent_id_is_null", "上级菜单不能为空"),
    ICON_IS_NULL("icon_is_null", "菜单图标不能为空"),
    PATH_IS_NULL("path_is_null", "菜单路径不能为空"),
    CODE_EXIST("code_exist", "菜单编码已存在"),
    NAME_EXIST("name_exist", "菜单名称已存在"),
    DELETE_CHILD_MENU_FIRST("delete_child_menu_first", "请先删除菜单下的子菜单"),
    ALREADY_BIND_ROLE("already_bind_role", "菜单已和角色绑定"),
    ;

    private final String code;
    private final String msg;

    MenuResponseCode(String code, String msg) {
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
