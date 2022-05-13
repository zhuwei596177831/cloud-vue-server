package com.example.business.service;

import com.example.business.openfeign.client.AccountFeignClient;
import com.example.business.openfeign.client.OrderFeignClient;
import com.example.core.entity.Json;
import com.example.core.vo.system.UserVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 朱伟伟
 * @date 2021-12-01 16:58:03
 * @description
 */
@Service
public class BusinessService {

    @Autowired
    private TestService testService;
    @Autowired
    private AccountFeignClient accountFeignClient;
    @Autowired
    private OrderFeignClient orderFeignClient;


    @GlobalTransactional(rollbackFor = Exception.class)
    public Json seataTest() {
        testService.saveTest();
        UserVo userVo = new UserVo();
        userVo.setName("顾柳霞");
        Json result = accountFeignClient.saveAccount("aaa", "朱伟伟", userVo);
        if (!result.isSuccess()) {
            throw new RuntimeException(result.getMsg());
        }
        //result = orderFeignClient.saveOrder();
        //if (result.isNotSuccess()) {
        //    throw new RuntimeException(result.getMsg());
        //}
        if (1 + 1 == 2) {
            throw new RuntimeException("顾柳霞");
        }
        return Json.success();
    }
}
