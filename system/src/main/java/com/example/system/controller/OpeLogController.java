package com.example.system.controller;

import com.example.core.entity.Json;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.entity.OpeLog;
import com.example.system.service.OpeLogService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2022-06-07 15:02:02
 * @description 操作日志记录Controller
 */
@Api(tags = "操作日志记录")
@ApiSupport(author = "朱伟伟")
@RestController
@RequestMapping("/opeLog")
public class OpeLogController extends BaseController {


    private final OpeLogService opeLogService;

    public OpeLogController(OpeLogService opeLogService) {
        this.opeLogService = opeLogService;
    }

    /**
     * 分页列表数据
     *
     * @param opeLog: 参数
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     **/
    @ApiOperation(value = "分页列表数据")
    @PostMapping("/page")
    public Json page(OpeLog opeLog) {
        List<OpeLog> page = opeLogService.list(opeLog, getPageInfo());
        return Json.ok(page);
    }

    /**
     * 列表数据
     *
     * @param opeLog: 参数
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     **/
    @ApiOperation(value = "列表数据")
    @PostMapping("/list")
    public Json list(OpeLog opeLog) {
        List<OpeLog> list = opeLogService.list(opeLog, null);
        return Json.ok(list);
    }

    /**
     * 根据主键id查询
     *
     * @param id: 主键id
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     **/
    @ApiOperation(value = "根据主键id查询")
    @PostMapping(value = "/selectById/{id}")
    public Json selectById(@PathVariable("id") Long id) {
        return Json.ok(opeLogService.selectById(id));
    }

    /**
     * 根据id删除
     *
     * @param id: 主键id
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     */
    @ApiOperation(value = "根据id删除")
    @PostMapping("/deleteById/{id}")
    public Json deleteById(@PathVariable("id") Long id) {
        return opeLogService.deleteById(id);
    }

    /**
     * 根据主键id数组批量删除
     *
     * @param ids: 主键数组
     * @author: 朱伟伟
     * @date: 2022-06-07 15:02:02
     **/
    @ApiOperation(value = "根据主键id数组批量删除")
    @PostMapping("/deleteByIds/{ids}")
    public Json deleteByIds(@PathVariable("ids") Long[] ids) {
        return opeLogService.deleteByIds(ids);
    }
}
