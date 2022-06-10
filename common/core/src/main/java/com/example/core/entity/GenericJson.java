package com.example.core.entity;


import com.example.core.util.Constants;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 朱伟伟
 * @date 2020-12-27 11:52:23
 * @description 带泛型的返回结果
 * 1、openfeign之间调用，带返回参数时，可以使用此类
 * 2、swagger文档，需要看到返回参数各个字段的详细信息时，可以使用此类
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "结果")
public class GenericJson<T> extends BaseEntity {
    private static final long serialVersionUID = -4845075787435077803L;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "消息")
    private String msg;
    @ApiModelProperty(value = "成功/失败标志")
    private boolean success;
    @ApiModelProperty(value = "数据")
    private T data;

    public GenericJson(String code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public GenericJson(String code, String msg, boolean success, T data) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public GenericJson(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public static <T> GenericJson<T> ok(T data) {
        return new GenericJson<>(Constants.SUCCESS_CODE_STRING, Constants.SUCCESS_MSG_STRING, true, data);
    }

    public static <E> GenericJson<PageData<E>> page(Page<E> page) {
        return new GenericJson<>(Constants.SUCCESS_CODE_STRING, Constants.SUCCESS_MSG_STRING,
                true, new PageData<>(page));
    }

    public static <E> GenericJson<PageData<E>> page(com.baomidou.mybatisplus.extension.plugins.pagination.Page<E> page) {
        return new GenericJson<>(Constants.SUCCESS_CODE_STRING, Constants.SUCCESS_MSG_STRING, true, new PageData<>(page));
    }

    public static <E> GenericJson<E> fail(String code, String msg) {
        return new GenericJson<>(code, msg, false);
    }

    public static <E> GenericJson<E> fail(String msg) {
        return new GenericJson<>(msg, false);
    }

    public static <E> GenericJson<E> success() {
        return new GenericJson<>(Constants.SUCCESS_MSG_STRING, true);
    }

    public static <E> GenericJson<E> success(String msg) {
        return new GenericJson<>(msg, true);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean notSuccess() {
        return !isSuccess();
    }

}
