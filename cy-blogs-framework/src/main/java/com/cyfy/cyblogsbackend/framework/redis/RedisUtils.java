package com.cyfy.cyblogsbackend.framework.redis;


public interface RedisUtils<T> {


    // Redis 常用类型的基础增删改查操作方法

    /**
     * 插入/修改
     *
     * @param key   存储键名
     * @param value 键对应的值
     */
    void set(String key, T value);


    /**
     * 添加单个元素
     *
     * @param key   存储键名
     * @param value 是否添加成功
     */
    boolean add(String key, String value);

    /**
     * 获取缓存中指定键的值
     *
     * @param key 存储键名
     * @return 键对应的值
     */
    T get(String key);

    /**
     * 判断缓存中是否存在指定键
     *
     * @param key 存储键名
     * @return 是否存在
     */
    boolean exists(String key);

    /**
     * 删除缓存中指定的键
     *
     * @param key 存储键名
     * @return 是否删除成功
     */
    boolean del(String key);
}
