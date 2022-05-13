package com.example.order.service;

import com.example.core.entity.Json;
import com.example.order.entity.Order;
import com.example.order.mapper.OrderMapper;
import com.example.order.openfeign.client.StorageFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 朱伟伟
 * @date 2021-12-05 16:18:49
 * @description
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private StorageFeignClient storageFeignClient;

    @Transactional(rollbackFor = Exception.class)
    public Json saveOrder() {
        Order order = new Order();
        order.setName("测试订单");
        orderMapper.insert(order);
        Json result = storageFeignClient.saveStorage();
        if (!result.isSuccess()) {
            throw new RuntimeException(result.getMsg());
        }
        return Json.success();
    }
}
