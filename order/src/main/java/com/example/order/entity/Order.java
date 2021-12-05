package com.example.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 朱伟伟
 * @date 2021-12-05 16:17:00
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "t_order_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 3066181946951530467L;

    private Long id;

    private String name;

}
