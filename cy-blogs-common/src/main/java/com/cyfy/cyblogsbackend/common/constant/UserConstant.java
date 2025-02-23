package com.cyfy.cyblogsbackend.common.constant;

/**
 * 用户相关常量
 */
public interface UserConstant {
    // 登录用户相关常量
    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";
    /**
     * Token令牌存储键
     */
    String TOKEN_KEY = "X-Token";

    // 权限相关常量
    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "USER";
    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "ADMIN";
}
