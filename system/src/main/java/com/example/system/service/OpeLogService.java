package com.example.system.service;

import com.example.core.entity.Json;
import com.example.core.entity.PageInfo;
import com.example.core.vo.system.OpeLogVo;
import com.example.api.system.entity.OpeLog;
import com.example.system.mapper.OpeLogMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2022-06-07 15:02:02
 * @description 操作日志记录Service
 */
@Service
public class OpeLogService {

    @Autowired
    private OpeLogMapper opeLogMapper;

    /**
     * 列表查询
     *
     * @param opeLog: 查询参数
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     */
    public List<OpeLog> list(OpeLog opeLog, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return opeLogMapper.list(opeLog);
    }

    /**
     * 根据主键id查询
     *
     * @param id: 主键
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     */
    public OpeLog selectById(Long id) {
        return opeLogMapper.selectById(id);
    }

    /**
     * 根据id删除
     *
     * @param id: 主键id
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     */
    @Transactional(rollbackFor = Exception.class)
    public Json deleteById(Long id) {
        opeLogMapper.deleteById(id);
        return Json.success();
    }

    /**
     * 根据主键id数组批量删除
     *
     * @param ids: 主键数组
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json deleteByIds(Long[] ids) {
        opeLogMapper.deleteBatchIds(Arrays.asList(ids));
        return Json.success();
    }

    /**
     * 记录操作日志
     *
     * @param opeLogVo:
     * @author: 朱伟伟
     * @date: 2022-06-07 15:09
     **/
    @Async
    public void insertLog(OpeLogVo opeLogVo) {
        OpeLog opeLog = new OpeLog();
        BeanUtils.copyProperties(opeLogVo, opeLog);
        opeLogMapper.insert(opeLog);
    }
}
