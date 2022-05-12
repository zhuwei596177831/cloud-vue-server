package com.example.storage.service;

import com.example.core.entity.Json;
import com.example.storage.entity.Storage;
import com.example.storage.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 朱伟伟
 * @date 2021-12-05 16:36:45
 * @description
 */
@Service
public class StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Transactional(rollbackFor = Exception.class)
    public Json saveStorage() {
        Storage storage = new Storage();
        storage.setName("测试仓储");
        storageMapper.insert(storage);
        //if (1 + 1 == 2) {
        //    throw new RuntimeException("测试仓储");
        //}
        return Json.success();
    }
}
