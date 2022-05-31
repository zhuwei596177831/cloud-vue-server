package com.example.core.vo.system.file;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件信息
 *
 * @author ruoyi
 */
@Data
public class FileVo implements Serializable {
    private static final long serialVersionUID = 7648438310132448740L;
    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件类型
     */
    private String type;

}
