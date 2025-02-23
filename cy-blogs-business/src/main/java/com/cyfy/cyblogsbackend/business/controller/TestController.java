package com.cyfy.cyblogsbackend.business.controller;

import com.cyfy.cyblogsbackend.common.annotation.AuthCheck;
import com.cyfy.cyblogsbackend.common.constant.UserConstant;
import com.cyfy.cyblogsbackend.common.enums.UserRoleEnum;
import com.cyfy.cyblogsbackend.common.result.BaseResponse;
import com.cyfy.cyblogsbackend.common.result.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public BaseResponse<String> test() {
        return ResultUtils.success("所有人可访问");
    }

    @GetMapping("/test1")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<String> test1() {
        return ResultUtils.success("普通用户访问");
    }

    @GetMapping("/test2")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> test2() {
        return ResultUtils.success("管理员用户访问");
    }
}
