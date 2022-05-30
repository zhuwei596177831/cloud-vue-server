package com.example.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 *
 * @author ruoyi
 */
public interface FileService {
    /**
     * 单个文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    String uploadFile(MultipartFile file) throws Exception;

}
