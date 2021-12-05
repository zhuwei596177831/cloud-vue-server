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
    @ApiModelProperty(value = "数据")
    private T data;

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>("0000", "成功", null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>("0000", "成功", data);
    }

    public static <E> Result<ArrayData<E>> ok(Collection<E> data) {
        if (data instanceof Page) {
            Page<E> page = (Page<E>) data;
            return new Result<>("0000", "成功", new ArrayData<>(page));
        }
        return new Result<>("0000", "成功", new ArrayData<>(data));
    }

    public static <E> Result<ArrayData<E>> ok(com.baomidou.mybatisplus.extension.plugins.pagination.Page<E> page) {
        return new Result<>("0000", "成功", new ArrayData<>(page));
    }

    public boolean isSuccess() {
        return this.getCode().equals("0000");
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

}
