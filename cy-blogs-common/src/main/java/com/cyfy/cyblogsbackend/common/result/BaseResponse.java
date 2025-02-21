package com.cyfy.cyblogsbackend.common.result;

import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用响应类
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    /**
     * 返回状态码、数据、消息
     *
     * @param code    状态码
     * @param data    数据
     * @param message 消息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 只返回数据和状态码
     *
     * @param code 状态码
     * @param data 数据
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 只返回状态码和消息
     *
     * @param errorCode 错误状态信息
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}