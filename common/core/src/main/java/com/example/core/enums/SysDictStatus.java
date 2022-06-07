package com.example.core.enums;

import lombok.Getter;

/**
 * @author 朱伟伟
 * @date 2022-05-29 19:00:29
 * @description 字典类型 状态枚举
 */
@Getter
public enum SysDictStatus {

    NORMAL("0", "正常"),
    DISABLE("1", "停用"),
    ;

    private final String code;
    private final String name;

    SysDictStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        for (SysDictStatus sysDictStatus : values()) {
            if (sysDictStatus.getCode().equals(code)) {
                return sysDictStatus.getName();
            }
        }
        return null;
    }

}
