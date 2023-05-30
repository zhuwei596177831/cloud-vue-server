package com.example.coreweb.cache.customizers;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.stereotype.Component;

/**
 * 设置该CacheManager是否应该公开事务感知缓存对象
 * 默认为false，将其设置为“true”，将缓存操作与正在进行的spring管理的事务同步，
 * 仅在事务提交(afterCommit)后执行实际的put、evict、clear操作
 *
 * @author 朱伟伟
 * @date 2023-05-30 14:10:58
 */
@Component
public class TransactionAwareCacheManagerCustomizer implements CacheManagerCustomizer<AbstractTransactionSupportingCacheManager> {

    @Override
    public void customize(AbstractTransactionSupportingCacheManager cacheManager) {
        cacheManager.setTransactionAware(true);
    }

}
