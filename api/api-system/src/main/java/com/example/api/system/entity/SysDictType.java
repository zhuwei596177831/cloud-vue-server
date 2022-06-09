package com.example.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.core.entity.BaseEntity;
import com.example.core.enums.SysDictStatus;
import com.example.core.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 字典类型表 sys_dict_type
 *
 * @author ruoyi
 */
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "sys_dict_type")
@ApiModel(value = "字典类型")
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId
    private Long dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 状态名称（0正常 1停用）
     */
    @TableField(exist = false)
    private String statusName;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    public void setStatus(String status) {
        this.status = status;
        this.statusName = SysDictStatus.getName(status);
    }
}
