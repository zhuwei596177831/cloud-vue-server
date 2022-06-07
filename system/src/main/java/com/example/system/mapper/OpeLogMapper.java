package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.OpeLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2022-06-07 15:02:02
 * @description 操作日志记录Mapper接口
 */
@Mapper
public interface OpeLogMapper extends BaseMapper<OpeLog> {

    /**
     * 查询操作日志记录列表
     *
     * @param opeLog 操作日志记录
     * @return 操作日志记录集合
     */
    List<OpeLog> list(OpeLog opeLog);

}
