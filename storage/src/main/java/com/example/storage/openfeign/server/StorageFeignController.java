package com.example.storage.openfeign.server;

import com.example.core.entity.Json;
import com.example.storage.service.StorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2021-12-05 16:19:44
 * @description
 */
@RestController
@RequestMapping("/openFeign/storage")
public class StorageFeignController {

    private final StorageService storageService;

    public StorageFeignController(StorageService storageService) {
        this.storageService = storageService;
    }


    @PostMapping("/saveStorage")
    public Json saveStorage() {
        return storageService.saveStorage();
    }

}
