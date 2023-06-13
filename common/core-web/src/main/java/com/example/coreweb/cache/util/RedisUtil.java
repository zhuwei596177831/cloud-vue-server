package com.example.coreweb.cache.util;

import com.example.core.util.Constants;
import com.example.coreweb.cache.standalone.redis.JacksonRedisTemplate;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author: 朱伟伟
 * @date: 2022-06-08 10:23
 **/
@Component
@Slf4j
public final class RedisUtil {

    @Autowired
    private JacksonRedisTemplate jacksonRedisTemplate;

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                jacksonRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return jacksonRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return jacksonRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */

    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                jacksonRedisTemplate.delete(key[0]);
            } else {
                jacksonRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @SuppressWarnings({"unchecked"})
    public <T> T get(String key, Class<T> clazz) {
        return key == null ? null : (T) jacksonRedisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存获取
     */
    @SuppressWarnings({"unchecked"})
    public <T> List<T> getList(String key, Class<T> clazz) {
        return key == null ? null : (List<T>) jacksonRedisTemplate.opsForValue().get(key);
    }


    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            jacksonRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                jacksonRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取指定前缀的一系列key
     * 使用scan命令代替keys, Redis是单线程处理，keys命令在KEY数量较多时，
     * 操作效率极低【时间复杂度为O(N)】，该命令一旦执行会严重阻塞线上其它命令的正常请求
     *
     * @param keyPrefix
     * @return
     */
    public Set<String> scanKeys(String keyPrefix) {
        String realKey = keyPrefix;
        try {
            return jacksonRedisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                Set<String> binaryKeys = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(realKey).count(Constants.REDIS_SCAN_SIZE).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;

            });
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 批量根据key模糊匹配删除
     *
     * @param key
     */
    public void delPattern(String key) {
        Set<String> keys = this.scanKeys(key);
        if (keys != null && keys.size() > 0) {
            this.multiDel(keys);
        }
    }


    /**
     * pipeline 批量设置值
     *
     * @param dataMap
     * @return
     */
    public List<Object> multiSet(Map<String, Object> dataMap) {
        log.info("multiSet开始,待写入数据keys，{}条,", dataMap.size());
        // 开始时间
        long start = System.currentTimeMillis();
        List<Object> result = jacksonRedisTemplate.executePipelined(new SessionCallback() {
            //执行流水线
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //批量处理的内容
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {

                    operations.opsForValue().set(entry.getKey(), entry.getValue());
                }
                //注意这里一定要返回null，最终pipeline的执行结果，才会返回给最外层
                return null;
            }
        });
        // 结束时间
        long end = System.currentTimeMillis();
        log.info("multiSet运行时间：{},写入{}条记录", (end - start), dataMap.size());
        return result;
    }

    /**
     * pipeline 批量设置值
     *
     * @param dataMap
     * @return
     */
    public List<Object> multiSetPage(Map<String, Object> dataMap) {
        log.info("multiSet开始,待写入数据keys，{}条,", dataMap.size());
        // 开始时间
        long start = System.currentTimeMillis();
        List<Object> result = jacksonRedisTemplate.executePipelined(new SessionCallback() {
            //执行流水线
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //批量处理的内容
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {

                    operations.opsForValue().set(entry.getKey(), entry.getValue());
                }
                //注意这里一定要返回null，最终pipeline的执行结果，才会返回给最外层
                return null;
            }
        });
        // 结束时间
        long end = System.currentTimeMillis();
        log.info("multiSet运行时间：{},写入{}条记录", (end - start), dataMap.size());
        return result;
    }

    /**
     * pipeline 批量删除
     *
     * @param keys
     * @return
     */
    public List<Object> multiDel(Collection<String> keys) {
        log.info("multiDel开始");
        // 开始时间
        long start = System.currentTimeMillis();
        List<Object> result = jacksonRedisTemplate.executePipelined(new SessionCallback() {
            //执行流水线
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //批量处理的内容
                keys.stream().forEach(redisKey -> {
                    operations.opsForValue().set(redisKey, null);
//                    operations.delete(redisKey);
                });
                //注意这里一定要返回null，最终pipeline的执行结果，才会返回给最外层
                return null;
            }
        });
        // 结束时间
        long end = System.currentTimeMillis();
        log.info("multiDel运行时间：{},删除{}条记录", (end - start), keys.size());
        return result;
    }


    /**
     * @param keys
     * @return
     */
    public Map<String, Object> batchQueryByKeys(List<String> keys) {
        long start = System.currentTimeMillis();
        log.info("batchQueryByKeys开始");
        if (null == keys || keys.size() == 0) {
            return null;
        }
        List<Object> results = jacksonRedisTemplate.executePipelined(
                new SessionCallback<Object>() {
                    @Override
                    public Object execute(RedisOperations operations) throws DataAccessException {
                        keys.stream().forEach(redisKey -> {
                            operations.opsForValue().get(redisKey);
                        });
                        return null;
                    }
                });
        if (null == results || results.size() == 0) {
            return null;
        }
        Map<String, Object> resultMapTwo = new HashMap<>();
        for (String t : keys) {
            resultMapTwo.put(t, results.get(keys.indexOf(t)));
        }
        // 结束时间
        long end = System.currentTimeMillis();
        log.info("batchQueryByKeys运行时间：{},查询{}条记录", (end - start), keys.size());
        return resultMapTwo;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return jacksonRedisTemplate.opsForValue().increment(key, delta);
    }


    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return jacksonRedisTemplate.opsForValue().increment(key, -delta);
    }


    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    @SuppressWarnings({"unchecked"})
    public <T> T hGet(String key, String item, Class<T> clazz) {
        return (T) jacksonRedisTemplate.opsForHash().get(key, item);
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    @SuppressWarnings({"unchecked"})
    public <T> List<T> hGetList(String key, String item, Class<T> clazz) {
        return (List<T>) jacksonRedisTemplate.opsForHash().get(key, item);
    }


    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmGet(String key) {
        return jacksonRedisTemplate.opsForHash().entries(key);
    }


    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmSet(String key, Map<String, Object> map) {
        try {
            jacksonRedisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmSet(String key, Map<String, Object> map, long time) {
        try {
            jacksonRedisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            jacksonRedisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            jacksonRedisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... item) {
        jacksonRedisTemplate.opsForHash().delete(key, item);
    }


    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return jacksonRedisTemplate.opsForHash().hasKey(key, item);
    }


    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hIncr(String key, String item, double by) {
        return jacksonRedisTemplate.opsForHash().increment(key, item, by);
    }


    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hDecr(String key, String item, double by) {
        return jacksonRedisTemplate.opsForHash().increment(key, item, -by);
    }


    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    @SuppressWarnings("unchecked")
    public <E> Set<E> sGet(String key) {
        try {
            return (Set<E>) jacksonRedisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return jacksonRedisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return jacksonRedisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = jacksonRedisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return jacksonRedisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {

        try {

            Long count = jacksonRedisTemplate.opsForSet().remove(key, values);

            return count;

        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }

    // ===============================list=================================


    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> lGet(String key, long start, long end) {
        try {
            return (List<E>) jacksonRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return jacksonRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return jacksonRedisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            jacksonRedisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            jacksonRedisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            jacksonRedisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            jacksonRedisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            jacksonRedisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = jacksonRedisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取流水号
     *
     * @param value
     * @return
     */
    public long getSerialNumber(String value) {
        try {
            Long serialNumber = jacksonRedisTemplate.opsForValue().increment(value);
            return serialNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 批量删除一组key
     *
     * @param keyPrefix key前缀
     */
    public long deleteGroup(String keyPrefix) {
        if (StringUtil.isNotEmpty(keyPrefix)) {
            Set<String> keys = jacksonRedisTemplate.keys(keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                long count = jacksonRedisTemplate.delete(keys);
                log.info("--------需清除Redis中分组:{}, key个数：{},清除成功个数:{}", keyPrefix, keys.size(), count);
                return count;
            } else {
                log.info("--------需清除Redis中分组:{}, key个数为 0,无需清除.", keyPrefix);
            }
        }
        return 0L;
    }
}



