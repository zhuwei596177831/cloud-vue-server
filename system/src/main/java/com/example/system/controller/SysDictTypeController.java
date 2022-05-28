package com.example.system.controller;

import com.example.core.entity.Json;
import com.example.core.util.Constants;
import com.example.shiroAuthencation.controller.BaseController;
import com.example.system.entity.SysDictType;
import com.example.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/dict/type")
public class SysDictTypeController extends BaseController {
    @Autowired
    private SysDictTypeService dictTypeService;

    @PostMapping("/list")
    public Json list(SysDictType dictType) {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType, getPageInfo());
        return Json.ok(list);
    }

    /**
     * 查询字典类型详细
     */
    @GetMapping(value = "/{dictId}")
    public Json getInfo(@PathVariable Long dictId) {
        return Json.ok(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public Json add(@Validated @RequestBody SysDictType dict) {
        if (Constants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return Json.fail("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(getUser().getName());
        return Json.ok(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @PutMapping
    public Json edit(@Validated @RequestBody SysDictType dict) {
        if (Constants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return Json.fail("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(getUser().getName());
        return Json.ok(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public Json remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return Json.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public Json optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return Json.ok(dictTypes);
    }
}
