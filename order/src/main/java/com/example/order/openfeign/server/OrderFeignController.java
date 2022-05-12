package com.example.order.openfeign.server;

import com.example.core.entity.Json;
import com.example.order.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2021-12-05 16:19:44
 * @description
 */
@RestController
@RequestMapping("/openFeign/order")
public class OrderFeignController {

    private final OrderService orderService;

    public OrderFeignController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/saveOrder")
    public Json saveOrder() {
        return orderService.saveOrder();
    }

}
