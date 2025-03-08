package com.cyfy.cyblogsbackend.framework.redis;

import cn.hutool.core.util.StrUtil;
import com.cyfy.cyblogsbackend.common.exception.BusinessException;
import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;

@Slf4j
public class RedisUtilsProxy<T> implements RedisUtils<T> {

    private RedisUtils<T> redisUtils;

    public RedisUtilsProxy(RedisUtils<T> redisUtils) {
        this.redisUtils = redisUtils;
    }


    /**
     * 插入/修改
     *
     * @param key   存储键名
     * @param value 存储键对应的值
     */
    @Override
    public void set(String key, T value) {
        if (StrUtil.isBlank(key)) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "键名不能为空");
        }
        try {
            log.info("正在进行 Redis 缓存数据插入/修改操作");
            redisUtils.set(key, value);
            log.info("已完成 Redis 缓存数据插入/修改操作");
        } catch (BusinessException e) {
            // 外层还需要捕获一次自定义异常，不然会被Exception覆盖
            throw new BusinessException(e.getCode(), e.getMessage());
        } catch (RedisConnectionFailureException e) {
            throw new BusinessException(ErrorCode.REDIS_CONNECTION_ERROR);
        } catch (NullPointerException e) {
            log.error("Null pointer exception occurred", e);
            throw new BusinessException(ErrorCode.REDIS_ERROR);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.REDIS_ERROR);
        }
    }

    /**
     * 添加单个元素
     *
     * @param key   存储键名
     * @param value 存储键对应的值
     * @return 返回是否添加成功
     */
    @Override
    public boolean add(String key, String value) {
        if (StrUtil.isBlank(key)) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "键名不能为空");
        }
        try {
            log.info("正在进行 Redis 缓存数添加操作");
            boolean result = redisUtils.add(key, value);
            log.info("已完成 Redis 缓存数添加操作");
            return result;
        } catch (BusinessException e) {
            // 外层还需要捕获一次自定义异常，不然会被Exception覆盖
            throw new BusinessException(e.getCode(), e.getMessage());
        } catch (RedisConnectionFailureException e) {
            throw new BusinessException(ErrorCode.REDIS_CONNECTION_ERROR);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.REDIS_ERROR);
        }
    }

    /**
     * 获取缓存中指定键的值
     *
     * @param key 存储键名
     * @return 存储键对应的值
     */
    @Override
    public T get(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "键名不能为空");
        }
        try {
            log.info("正在进行 Redis 缓存数查询操作");
            T result = redisUtils.get(key);
            log.info("已完成 Redis 缓存数查询操作，返回数据为：{}", result);
            return result;
        } catch (BusinessException e) {
            // 外层还需要捕获一次自定义异常，不然会被Exception覆盖
            throw new BusinessException(e.getCode(), e.getMessage());
        } catch (RedisConnectionFailureException e) {
            throw new BusinessException(ErrorCode.REDIS_CONNECTION_ERROR);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.REDIS_ERROR);
        }
    }

    /**
     * 判断缓存中是否存在指定键
     *
     * @param key 存储键名
     * @return 是否存在
     */
    @Override
    public boolean exists(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "键名不能为空");
        }
        try {
            log.info("正在进行 Redis 缓存数据判断存在操作");
            boolean result = redisUtils.exists(key);
            log.info("正在进行 Redis 缓存数据判断存在操作");
            return result;
        } catch (BusinessException e) {
            // 外层还需要捕获一次自定义异常，不然会被Exception覆盖
            throw new BusinessException(e.getCode(), e.getMessage());
        } catch (RedisConnectionFailureException e) {
            throw new BusinessException(ErrorCode.REDIS_CONNECTION_ERROR);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.REDIS_ERROR);
        }
    }

    /**
     * 删除缓存中指定的键
     *
     * @param key 存储键名
     * @return 是否删除成功
     */
    @Override
    public boolean del(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BusinessException(ErrorCode.REDIS_ERROR, "键名不能为空");
        }
        try {
            log.info("正在进行 Redis 缓存数据删除操作");
            boolean result = redisUtils.del(key);
            log.info("已完成 Redis 缓存数据删除操作");
            return result;
        } catch (BusinessException e) {
            // 外层还需要捕获一次自定义异常，不然会被Exception覆盖
            throw new BusinessException(e.getCode(), e.getMessage());
        } catch (RedisConnectionFailureException e) {
            throw new BusinessException(ErrorCode.REDIS_CONNECTION_ERROR);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.REDIS_ERROR);
        }
    }
}
