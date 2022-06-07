package com.example.core.entity;


import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author 朱伟伟
 * @date 2020-12-27 11:52:23
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "结果")
public class Result<T> extends BaseEntity {
    private static final long serialVersionUID = -4845075787435077803L;
    @ApiModelProperty(value = "错误编码")
    private String code;
    @ApiModelProperty(value = "错误消息")
    private String msg;
    @ApiModelProperty(value = "成功/失败标志")
    private boolean success;
    @ApiModelProperty(value = "数据")
    private T data;

    public Result(String code, String msg, boolean success, T data) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>("0000", "成功", true, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>("0000", "成功", true, data);
    }

    public static <E> Result<PageData<E>> ok(Collection<E> data) {
        if (data instanceof Page) {
            Page<E> page = (Page<E>) data;
            return new Result<>("0000", "成功", true, new PageData<>(page));
        }
        return new Result<>("0000", "成功", true, new PageData<>(data));
    }

    public static <E> Result<PageData<E>> ok(com.baomidou.mybatisplus.extension.plugins.pagination.Page<E> page) {
        return new Result<>("0000", "成功", true, new PageData<>(page));
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

}
