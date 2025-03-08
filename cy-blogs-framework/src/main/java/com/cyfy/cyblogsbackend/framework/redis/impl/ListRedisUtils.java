package com.cyfy.cyblogsbackend.framework.redis.impl;

import cn.hutool.core.collection.CollUtil;
import com.cyfy.cyblogsbackend.common.exception.BusinessException;
import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import com.cyfy.cyblogsbackend.framework.redis.RedisUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Redis 列表类型工具类
 *
 * @param <T>
 */
@Component
public class ListRedisUtils<T> implements RedisUtils<List<T>> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 插入/修改
     *
     * @param key   存储键名
     * @param value 键对应的值
     */
    @Override
    public void set(String key, List<T> value) {
        // 将 List<T> 转换为 List<String>
        List<String> stringValues = value.stream().map(Object::toString).collect(Collectors.toList());
        if (CollUtil.isEmpty(stringValues)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "值不能为空");
        }
        stringRedisTemplate.opsForList().rightPushAll(key, stringValues.toArray(new String[0]));
    }

    /**
     * 添加单个元素
     *
     * @param key   存储键名
     * @param value 是否添加成功
     */
    @Override
    public boolean add(String key, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, value) > 0;
    }

    /**
     * 获取缓存中指定键的值
     *
     * @param key 存储键名
     * @return 键对应的值
     */
    @Override
    public List<T> get(String key) {
        List<String> stringValues = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (stringValues == null || stringValues.isEmpty()) {
            return new ArrayList<>();
        }
        // 将 List<String> 转换为 List<T>
        return stringValues.stream().map(s -> (T) s).collect(Collectors.toList());
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
