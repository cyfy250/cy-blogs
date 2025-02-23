package com.cyfy.cyblogsbackend.business.service;

import com.cyfy.cyblogsbackend.business.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyfy.cyblogsbackend.business.model.dto.user.UserLoginRequest;
import com.cyfy.cyblogsbackend.business.model.dto.user.UserRegisterRequest;
import com.cyfy.cyblogsbackend.business.model.vo.LoginUserVO;
import com.cyfy.cyblogsbackend.business.model.vo.UserInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cy
 * @description 针对表【user_info(用户信息表)】的数据库操作Service
 * @createDate 2025-02-21 21:46:22
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求参数
     * @return 新注册用户id
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求参数
     * @param request
     * @return 存有脱敏后的用户信息的token令牌
     */
    String userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 用户信息脱敏处理
     *
     * @param userInfo
     * @return
     */
    LoginUserVO getLoginUserVO(UserInfo userInfo);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    LoginUserVO getCurrentLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);
}
