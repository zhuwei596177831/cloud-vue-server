package com.example.coreweb.cache.customizers;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 设置CacheManager是否应该公开事务感知缓存对象
 * 默认为false，将其设置为“true”，将缓存操作与正在进行的spring管理的事务同步，
 * 仅在事务提交(afterCommit)后执行实际的put、evict、clear操作
 * <p/>
 * 修正：配了也没用，执行到put操作时，事务管理器已将当前线程的事务清理掉
 *
 * @author 朱伟伟
 * @date 2023-05-30 14:10:58
 * @see TransactionSynchronizationManager#clearSynchronization()
 */
//@Component
public class TransactionAwareCacheManagerCustomizer implements CacheManagerCustomizer<AbstractTransactionSupportingCacheManager> {

    @Override
    public void customize(AbstractTransactionSupportingCacheManager cacheManager) {
        cacheManager.setTransactionAware(true);
    }

}
