package com.cyfy.cyblogsbackend.common.result;

import com.cyfy.cyblogsbackend.common.exception.ErrorCode;

/**
 * 响应工具类
 */
public class ResultUtils {
    /***
     * 成功
     * @param data  数据
     * @param <T>   数据类型
     * @return 响应
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), data, ErrorCode.SUCCESS.getMessage());
    }

    /***
     * 成功 （不返回数据）
     * @param <T>   数据类型
     * @return 响应
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(ErrorCode.SUCCESS);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 响应
     */
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /***
     * 失败
     * @param code      错误码
     * @param message   错误信息
     * @return 响应
     */
    public static BaseResponse<?> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    /***
     * 失败
     * @param errorCode 错误码
     * @param message   错误信息
     * @return 响应
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }
}