package com.cyfy.cyblogsbackend.business.controller;

import com.cyfy.cyblogsbackend.business.domain.UserInfo;
import com.cyfy.cyblogsbackend.business.service.UserInfoService;
import com.cyfy.cyblogsbackend.common.exception.BusinessException;
import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import com.cyfy.cyblogsbackend.common.result.BaseResponse;
import com.cyfy.cyblogsbackend.common.result.ResultUtils;
import com.cyfy.cyblogsbackend.common.tools.EncipherUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/add")
    public BaseResponse<String> add(@RequestBody UserInfo userInfo) {
        // 获取用户密码
        String password = userInfo.getUserPassword();
        // 对密码进行加密
        userInfo.setUserPassword(EncipherUtils.hashPsd(password));
        userInfoService.save(userInfo);
        return ResultUtils.success();
    }

    // 登录
    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody UserInfo userInfo) {
        // 获取用户密码
        String password = userInfo.getUserPassword();
        // 查询用户是否存在
        UserInfo user = userInfoService.getById(userInfo.getUserId());
        if (user == null) {
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_EXIST);
        }
        // 对比密码是否一致
        if (!EncipherUtils.checkPsd(password, user.getUserPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        return ResultUtils.success();
    }

    @PostMapping("/get")
    public UserInfo get(long id) {

        return userInfoService.getById(id);
    }

    @PostMapping("/update")
    public String update(@RequestBody UserInfo userInfo) {
        userInfoService.updateById(userInfo);
        return "success";
    }

    @PostMapping("/delete")
    public String delete(long id) {
        userInfoService.removeById(id);
        return "success";
    }
}
