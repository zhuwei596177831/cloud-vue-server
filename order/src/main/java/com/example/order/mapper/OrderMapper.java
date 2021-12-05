package com.example.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 朱伟伟
 * @date 2021-12-05 16:18:27
 * @description
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
