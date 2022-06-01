package com.example.gen.controller;

import com.example.core.entity.Json;
import com.example.core.enums.DatabaseName;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2022-06-01 15:13:20
 * @description
 */
@RestController
@RequestMapping("/databaseName")
public class DatabaseNameController {

    /**
     * 列举所有的数据库 用于代码自动生成
     *
     * @author: 朱伟伟
     * @date: 2022-06-01 15:14
     **/
    @PostMapping("/list")
    public Json list() {
        return Json.ok(DatabaseName.getElSelectVos());
    }


}
