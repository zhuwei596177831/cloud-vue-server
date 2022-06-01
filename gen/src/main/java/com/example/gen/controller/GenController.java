package com.example.gen.controller;

import com.example.core.entity.Json;
import com.example.core.text.Convert;
import com.example.gen.entity.GenTable;
import com.example.gen.entity.GenTableColumn;
import com.example.gen.service.IGenTableColumnService;
import com.example.gen.service.IGenTableService;
import com.example.shiroAuthencation.controller.BaseController;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author ruoyi
 */
@RequestMapping("/gen")
@RestController
public class GenController extends BaseController {
    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;

    /**
     * 查询代码生成列表
     */
    @GetMapping("/list")
    public Json genList(GenTable genTable) {
        PageHelper.startPage(getPageInfo().getPageNum(), getPageInfo().getPageSize());
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return Json.ok(list);
    }

    /**
     * 修改代码生成业务
     */
    @GetMapping(value = "/{tableId}")
    public Json getInfo(@PathVariable Long tableId) {
        GenTable table = genTableService.selectGenTableById(tableId);
        List<GenTable> tables = genTableService.selectGenTableAll();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", table);
        map.put("rows", list);
        map.put("tables", tables);
        return Json.ok(map);
    }

    /**
     * 查询数据库列表
     */
    @GetMapping("/db/list")
    public Json dataList(GenTable genTable) {
        PageHelper.startPage(getPageInfo().getPageNum(), getPageInfo().getPageSize());
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return Json.ok(list);
    }

    /**
     * 查询数据表字段列表
     */
    @GetMapping(value = "/column/{tableId}")
    public Json columnList(Long tableId) {
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        return Json.ok(list);
    }

    /**
     * 导入表结构（保存）
     */
    @PostMapping("/importTable")
    public Json importTableSave(String tables, String databaseName) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames, databaseName);
        genTableService.importGenTable(tableList, databaseName, getUser());
        return Json.success();
    }

    /**
     * 修改保存代码生成业务
     */
    @PutMapping
    public Json editSave(@Validated @RequestBody GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return Json.success();
    }

    /**
     * 删除代码生成
     */
    @DeleteMapping("/{tableIds}")
    public Json remove(@PathVariable Long[] tableIds) {
        genTableService.deleteGenTableByIds(tableIds);
        return Json.success();
    }

    /**
     * 预览代码
     */
    @GetMapping("/preview/{tableId}")
    public Json preview(@PathVariable("tableId") Long tableId) throws IOException {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return Json.ok(dataMap);
    }

    /**
     * 生成代码（下载方式）
     */
    @GetMapping("/download/{tableName}/{databaseName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName,
                         @PathVariable("databaseName") String databaseName) throws IOException {
        byte[] data = genTableService.downloadCode(tableName, databaseName);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     */
    @GetMapping("/genCode/{tableName}/{databaseName}")
    public Json genCode(@PathVariable("tableName") String tableName,
                        @PathVariable("databaseName") String databaseName) {
        genTableService.generatorCode(tableName, databaseName);
        return Json.success();
    }

    /**
     * 同步数据库
     */
    @GetMapping("/synchDb/{tableName}/{databaseName}")
    public Json synchDb(@PathVariable("tableName") String tableName,
                        @PathVariable("databaseName") String databaseName) {
        genTableService.synchDb(tableName, databaseName);
        return Json.success();
    }

    /**
     * 批量生成代码
     */
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, @RequestParam String tables,
                             @RequestParam String databaseName) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.downloadCode(tableNames, databaseName);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
