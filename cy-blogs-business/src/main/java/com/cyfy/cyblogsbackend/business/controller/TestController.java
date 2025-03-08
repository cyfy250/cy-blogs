package com.cyfy.cyblogsbackend.business.controller;

import com.cyfy.cyblogsbackend.business.model.vo.LoginUserVO;
import com.cyfy.cyblogsbackend.common.annotation.AuthCheck;
import com.cyfy.cyblogsbackend.common.constant.UserConstant;
import com.cyfy.cyblogsbackend.common.enums.UserRoleEnum;
import com.cyfy.cyblogsbackend.common.result.BaseResponse;
import com.cyfy.cyblogsbackend.common.result.ResultUtils;
import com.cyfy.cyblogsbackend.framework.redis.RedisUtils;
import com.cyfy.cyblogsbackend.framework.redis.RedisUtilsProxy;
import com.cyfy.cyblogsbackend.framework.redis.impl.ListRedisUtils;
import com.cyfy.cyblogsbackend.framework.redis.impl.StringRedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private StringRedisUtils stringRedisUtils;

    @Resource
    private ListRedisUtils listRedisUtils;

    @GetMapping("/test")
    public BaseResponse<String> test() {
        RedisUtils redisUtils = new RedisUtilsProxy(stringRedisUtils);
        // 调用方法进行测试
        redisUtils.del("testKey");
        return ResultUtils.success("所有人可访问");
    }

    @GetMapping("/redis/list")
    public BaseResponse<String> testRedisList() {
        List<LoginUserVO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LoginUserVO loginUserVO = new LoginUserVO();
            loginUserVO.setUserId(Long.valueOf(i));
            loginUserVO.setUserAccount("userAccount" + i);
            loginUserVO.setUserName("userName" + i);
            loginUserVO.setUserProfile("userProfile" + i);
            loginUserVO.setUserRole("userRole" + i);
            loginUserVO.setUserEmail("userEmail" + i);
            list.add(loginUserVO);
        }
        RedisUtils proxy = new RedisUtilsProxy(listRedisUtils);
        proxy.set("loginUserVOList", list);
        proxy.del("loginUserVOList");
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
