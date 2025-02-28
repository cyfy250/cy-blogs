package com.cyfy.cyblogsbackend.common.exception;

import lombok.Getter;

/**
 * 状态码枚举类
 */
@Getter
public enum ErrorCode {
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    ACCOUNT_NOT_EXIST(40011, "账号不存在"),
    PASSWORD_ERROR(40012, "密码错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    TOKEN_ERROR(40120, "无效令牌"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
