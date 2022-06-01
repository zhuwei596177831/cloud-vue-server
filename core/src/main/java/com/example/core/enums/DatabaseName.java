package com.example.core.enums;

import com.example.core.vo.system.ElSelectVo;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2022-06-01 15:15:03
 * @description 所有数据库的枚举
 * 当分库时 代码自动生成工具 会用到
 */
@Getter
public enum DatabaseName {

    system("system", "系统库"),

    ;

    private final String code;
    private final String name;

    DatabaseName(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<ElSelectVo> getElSelectVos() {
        return Arrays.stream(values()).map(i -> {
            ElSelectVo elSelectVo = new ElSelectVo();
            elSelectVo.setCode(i.getCode());
            elSelectVo.setName(i.getName());
            return elSelectVo;
        }).collect(Collectors.toList());
    }

}
