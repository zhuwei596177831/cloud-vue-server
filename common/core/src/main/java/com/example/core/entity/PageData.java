package com.example.core.entity;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author 朱伟伟
 * @date 2020-12-27 12:02:17
 * @description result 集合数据
 */
@Getter
@Setter
public class PageData<T> extends BaseEntity {
    private static final long serialVersionUID = 403443358176094884L;
    /**
     * 数据
     */
    @ApiModelProperty(value = "集合数据")
    private Collection<T> data;
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private long pageNum;
    /**
     * 每页的数量
     */
    @ApiModelProperty(value = "每页的数量")
    private long pageSize;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "当前页")
    private long pages;
    /**
     * 是否有下一页
     */
    @ApiModelProperty(value = "是否有下一页")
    private boolean hasNextPage = false;
    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    private long total;

    /**
     * page helper分页方式
     */
    public PageData(Page<T> page) {
        this.data = page.getResult();
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.pages = page.getPages();
        this.hasNextPage = page.getPageNum() < page.getPages();
        this.total = page.getTotal();
    }

    /**
     * mybatis plus自带分页方式
     */
    public PageData(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page) {
        this.data = page.getRecords();
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.pages = page.getPages();
        this.hasNextPage = page.hasNext();
        this.total = page.getTotal();
    }

    public PageData(Collection<T> data) {
        this.data = data;
        this.pageNum = 1;
        this.pageSize = data.size();
        this.pages = data.size();
        this.total = data.size();
    }

}
