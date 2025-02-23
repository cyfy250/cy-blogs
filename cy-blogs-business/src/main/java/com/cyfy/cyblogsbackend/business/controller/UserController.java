package com.cyfy.cyblogsbackend.business.controller;

import cn.hutool.core.util.ObjUtil;
import com.cyfy.cyblogsbackend.business.domain.UserInfo;
import com.cyfy.cyblogsbackend.business.model.dto.user.UserLoginRequest;
import com.cyfy.cyblogsbackend.business.model.dto.user.UserRegisterRequest;
import com.cyfy.cyblogsbackend.business.model.vo.LoginUserVO;
import com.cyfy.cyblogsbackend.business.service.UserInfoService;
import com.cyfy.cyblogsbackend.common.exception.BusinessException;
import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import com.cyfy.cyblogsbackend.common.exception.ThrowUtils;
import com.cyfy.cyblogsbackend.common.result.BaseResponse;
import com.cyfy.cyblogsbackend.common.result.ResultUtils;
import com.cyfy.cyblogsbackend.common.tools.EncipherUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public BaseResponse<String> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String loginUserToken = userInfoService.userLogin(userLoginRequest, request);
        return ResultUtils.success(loginUserToken);
    }

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        long result = userInfoService.userRegister(userRegisterRequest);
        return ResultUtils.success(result);
    }

    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        LoginUserVO result = userInfoService.getCurrentLoginUser(request);
        return ResultUtils.success(result);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        boolean result = userInfoService.userLogout(request);
        return ResultUtils.success(result);
    }
}
