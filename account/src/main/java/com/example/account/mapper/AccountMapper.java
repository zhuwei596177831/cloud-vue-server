package com.example.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.account.entity.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 朱伟伟
 * @date 2021-12-01 16:52:11
 * @description
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
