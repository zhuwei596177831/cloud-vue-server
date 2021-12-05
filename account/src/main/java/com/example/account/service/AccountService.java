package com.example.account.service;

import com.example.account.entity.Account;
import com.example.account.mapper.AccountMapper;
import com.example.core.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 朱伟伟
 * @date 2021-12-01 16:52:34
 * @description
 */
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Transactional(rollbackFor = Exception.class)
    public Result save() {
        Account account = new Account();
        account.setName("测试账户");
        account.setNumber("6228210660073328211");
        accountMapper.insert(account);
        //if (1 + 1 == 2) {
        //    throw new RuntimeException("账户异常");
        //}
        //模拟超时
        //try {
        //    Thread.sleep(3000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        return Result.ok();
    }

}
