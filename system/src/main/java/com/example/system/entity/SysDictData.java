package com.example.system.entity;

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
 * 字典数据表 sys_dict_data
 *
 * @author ruoyi
 */
@TableName(value = "sys_dict_data")
@ApiModel(value = "字典类型")
@Getter
@Setter
@NoArgsConstructor
public class SysDictData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableId
    private Long dictCode;

    /**
     * 字典排序
     */
    private Long dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格字典样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

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


    public boolean getDefault() {
        return Constants.YES.equals(this.isDefault);
    }

    public void setStatus(String status) {
        this.status = status;
        this.statusName = SysDictStatus.getName(status);
    }

}
