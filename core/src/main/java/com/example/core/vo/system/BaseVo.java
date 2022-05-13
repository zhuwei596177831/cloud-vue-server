package com.example.core.vo.system;

import com.example.core.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 朱伟伟
 * @date 2021-11-28 23:28:19
 * @description
 */
@Getter
@Setter
public class BaseVo implements Serializable {
    private static final long serialVersionUID = 3552826897110198134L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    protected Long id;

    /**
     * 录入用户
     */
    @ApiModelProperty(value = "录入用户")
    protected Long inputUserId;

    /**
     * 录入时间
     */
    @ApiModelProperty(value = "录入时间")
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    protected LocalDateTime inputTime;

    /**
     * 修改用户
     */
    @ApiModelProperty(value = "修改用户")
    protected Long updateUserId;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    protected LocalDateTime updateTime;

}
