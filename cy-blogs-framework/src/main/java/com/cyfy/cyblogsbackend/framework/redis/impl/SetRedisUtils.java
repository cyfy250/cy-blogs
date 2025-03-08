package com.cyfy.cyblogsbackend.framework.redis.impl;

import cn.hutool.core.util.StrUtil;
import com.cyfy.cyblogsbackend.common.exception.BusinessException;
import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import com.cyfy.cyblogsbackend.framework.redis.RedisUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Redis 集合类型工具类
 */
@Component
public class SetRedisUtils implements RedisUtils<Set<String>> {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加/修改集合
     *
     * @param key   存储键名
     * @param value 存储值
     */
    @Override
    public void set(String key, Set<String> value) {
        if (value == null || value.isEmpty()) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "集合元素值不能为空");
        }
        stringRedisTemplate.opsForSet().add(key, value.toArray(new String[0]));
    }

    /**
     * 添加单个元素
     *
     * @param key   存储键名
     * @param value 存储值
     */
    @Override
    public boolean add(String key, String value) {
        if (StrUtil.isBlank(value)) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "集合元素值不能为空");
        }
        // 添加集合元素
        Long result = stringRedisTemplate.opsForSet().add(key, value);
        return result != null && result > 0;
    }


    /**
     * 获取缓存中指定键的值
     *
     * @param key 存储键名
     * @return 键对应的值
     */
    @Override
    public Set<String> get(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "集合键不能为空");
        }
        return stringRedisTemplate.opsForSet().members(key);
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
