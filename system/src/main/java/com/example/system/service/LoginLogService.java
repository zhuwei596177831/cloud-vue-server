package com.example.system.service;

import com.example.core.entity.Json;
import com.example.core.entity.PageInfo;
import com.example.core.vo.system.LoginLogVo;
import com.example.api.system.entity.LoginLog;
import com.example.system.mapper.LoginLogMapper;
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
 * @date 2022-06-07 13:45:03
 * @description 登录日志记录Service
 */
@Service
public class LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 列表查询
     *
     * @param loginLog: 查询参数
     * @author: 朱伟伟
     * @date: 2022-06-07 13:45:03
     */
    public List<LoginLog> list(LoginLog loginLog, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return loginLogMapper.list(loginLog);
    }

    /**
     * 根据主键id查询
     *
     * @param id: 主键
     * @author: 朱伟伟
     * @date: 2022-06-07 13:45:03
     */
    public LoginLog selectById(Long id) {
        return loginLogMapper.selectById(id);
    }

    /**
     * 新增
     *
     * @param loginLog: 参数
     * @author: 朱伟伟
     * @date: 2022-06-07 13:45:03
     */
    @Transactional(rollbackFor = Exception.class)
    public Json insert(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
        return Json.success();
    }

    /**
     * 根据id删除
     *
     * @param id: 主键id
     * @author: 朱伟伟
     * @date: 2022-06-07 13:45:03
     */
    @Transactional(rollbackFor = Exception.class)
    public Json deleteById(Long id) {
        loginLogMapper.deleteById(id);
        return Json.success();
    }

    /**
     * 根据主键id数组批量删除
     *
     * @param ids: 主键数组
     * @author: 朱伟伟
     * @date: 2022-06-07 13:45:03
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json deleteByIds(Long[] ids) {
        loginLogMapper.deleteBatchIds(Arrays.asList(ids));
        return Json.success();
    }

    /**
     * 记录登录日志
     *
     * @param loginLogVo:
     * @author: 朱伟伟
     * @date: 2022-06-07 14:55
     **/
    @Async
    public void insertLog(LoginLogVo loginLogVo) {
        LoginLog loginLog = new LoginLog();
        BeanUtils.copyProperties(loginLogVo, loginLog);
        loginLogMapper.insert(loginLog);
    }
}
