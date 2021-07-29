package com.example.core.enums;

import com.example.core.entity.EnumModel;
import lombok.Getter;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2021-06-14 17:05:52
 * @description 菜单类型
 */
@Getter
public enum MenuType {

    MENU_MODEL(1, "模块菜单"),
    MENU_NAVIGATION(2, "导航菜单"),
    MENU_BUTTON(3, "功能按钮"),
    ;

    private final Integer value;
    private final String desc;

    MenuType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer value) {
        for (MenuType menuType : values()) {
            if (menuType.value.equals(value)) {
                return menuType.desc;
            }
        }
        return "";
    }

    public static List<EnumModel> toEnumModel() {
        return Arrays.stream(values()).map(i -> {
            EnumModel enumModel = new EnumModel();
            BeanUtils.copyProperties(i, enumModel);
            return enumModel;
        }).collect(Collectors.toList());
    }

}
