package com.example.business.controller;

import com.example.business.service.BusinessService;
import com.example.core.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2021-11-30 10:47:38
 * @description
 */
@RestController
@RequestMapping("/test")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/seataTest")
    public Result seataTest() {
        return businessService.seataTest();
    }

}
