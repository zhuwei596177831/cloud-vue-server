package com.example.core.entity;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author 朱伟伟
 * @date 2022-02-23 14:00:53
 * @description
 */
@Data
public class ObjectPageData implements Serializable {
    private static final long serialVersionUID = 665563421721268020L;

    /**
     * 当前页
     */
    private long pageNum;

    /**
     * 每页的数量
     */
    private long pageSize;

    /**
     * 总数
     */
    private long total;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 是否有前一页
     */
    private boolean hasPreviousPage;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    private Collection<Object> data;

    public ObjectPageData(Page<Object> page) {
        PageInfo<Object> pageInfo = new PageInfo<>(page);
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
        this.pages = pageInfo.getPages();
        this.hasPreviousPage = pageInfo.isHasPreviousPage();
        this.hasNextPage = pageInfo.isHasNextPage();
        this.data = page.getResult();
    }

    public ObjectPageData(com.baomidou.mybatisplus.extension.plugins.pagination.Page<Object> page) {
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.hasPreviousPage = page.hasPrevious();
        this.hasNextPage = page.hasNext();
        this.data = page.getRecords();
    }

}
