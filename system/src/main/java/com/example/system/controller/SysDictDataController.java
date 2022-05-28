package com.example.system.controller;

import com.example.core.entity.Json;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.entity.SysDictData;
import com.example.system.service.SysDictDataService;
import com.example.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/dict/data")
public class SysDictDataController extends BaseController {

    @Autowired
    private SysDictDataService dictDataService;
    @Autowired
    private SysDictTypeService dictTypeService;

    @GetMapping("/list")
    public Json list(SysDictData dictData) {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData, getPageInfo());
        return Json.ok(list);
    }

    /**
     * 查询字典数据详细
     */
    @GetMapping(value = "/{dictCode}")
    public Json getInfo(@PathVariable Long dictCode) {
        return Json.ok(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public Json dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isEmpty(data)) {
            data = new ArrayList<>();
        }
        return Json.ok(data);
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public Json add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateBy(getUser().getName());
        return Json.ok(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PutMapping
    public Json edit(@Validated @RequestBody SysDictData dict) {
        dict.setUpdateBy(getUser().getName());
        return Json.ok(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictCodes}")
    public Json remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return Json.success();
    }
}
