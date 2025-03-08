package com.cyfy.cyblogsbackend.framework.redis.impl;

import cn.hutool.core.util.StrUtil;
import com.cyfy.cyblogsbackend.common.exception.BusinessException;
import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import com.cyfy.cyblogsbackend.framework.redis.RedisUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Redis字符串类型工具类
 */
@Component
public class StringRedisUtils implements RedisUtils<String> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 插入缓存
     *
     * @param key   存储键名
     * @param value 存储值
     */
    @Override
    public void set(String key, String value) {
        if (StrUtil.isBlank(value)) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "值不能为空");
        }
        // 存入字符串类型
        stringRedisTemplate.opsForValue().set(key, value);

    }


    /**
     * 添加单个元素
     *
     * @param key   存储键名
     * @param value 存储值
     * @return 返回是否添加成功
     */
    @Override
    public boolean add(String key, String value) {
        Integer result = stringRedisTemplate.opsForValue().append(key, value);
        return result != null && result > 0;
    }

    /**
     * 获取缓存中指定键的值
     *
     * @param key 存储键名
     * @return 键对应的值
     */
    @Override
    public String get(String key) {
        // 可以不判断键是否存在，因为如果键不存在，opsForValue().get(key)会直接返回null
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 判断缓存中是否存在指定键
     *
     * @param key 存储键名
     * @return 是否存在
     */
    @Override
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 删除缓存中指定的键
     *
     * @param key 存储键名
     * @return 是否删除成功
     */
    @Override
    public boolean del(String key) {
        if (this.exists(key)) {
            return stringRedisTemplate.delete(key);
        }
        // 指定键本身就不存在，则视为已删
        return true;
    }
}
