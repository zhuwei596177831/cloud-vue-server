package com.example.file.controller;

import com.example.core.entity.Json;
import com.example.core.vo.system.file.FileVo;
import com.example.file.service.FileService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件请求处理
 *
 * @author ruoyi
 */
@Api(tags = "文件上传")
@ApiSupport(author = "朱伟伟")
@RestController
public class FileController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private FileService fileService;

    /**
     * 单个文件上传
     *
     * @param file:
     * @author: 朱伟伟
     * @date: 2022-05-30 14:58
     **/
    @ApiOperation(value = "单个文件上传")
    @PostMapping("/upload")
    public Json upload(MultipartFile file) throws Exception {
        // 上传并返回访问地址
        String url = fileService.uploadFile(file);
        FileVo fileVo = new FileVo();
        fileVo.setName(file.getOriginalFilename());
        if (file.getOriginalFilename() != null) {
            fileVo.setType(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        }
        fileVo.setUrl(url);
        fileVo.setSize(file.getSize());
        return Json.ok(fileVo);
    }

    /**
     * 多个文件上传
     *
     * @param files:
     * @author: 朱伟伟
     * @date: 2022-05-30 14:58
     **/
    @ApiOperation(value = "多个文件上传")
    @PostMapping("/multiUpload")
    public Json multiUpload(MultipartFile[] files) throws Exception {
        List<FileVo> fileVos = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = fileService.uploadFile(file);
            FileVo fileVo = new FileVo();
            fileVo.setName(file.getOriginalFilename());
            fileVo.setUrl(url);
            fileVo.setSize(file.getSize());
            fileVos.add(fileVo);
        }
        return Json.ok(fileVos);
    }
}