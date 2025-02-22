package com.cyfy.cyblogsbackend.business.controller;

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
        return ResultUtils.success("test");
    }
}
