package com.cyfy.cyblogsbackend.business.service.impl;
import java.util.Date;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyfy.cyblogsbackend.business.domain.UserInfo;
import com.cyfy.cyblogsbackend.business.model.dto.user.UserLoginRequest;
import com.cyfy.cyblogsbackend.business.model.dto.user.UserRegisterRequest;
import com.cyfy.cyblogsbackend.business.model.vo.LoginUserVO;
import com.cyfy.cyblogsbackend.business.service.UserInfoService;
import com.cyfy.cyblogsbackend.business.mapper.UserInfoMapper;
import com.cyfy.cyblogsbackend.common.constant.UserConstant;
import com.cyfy.cyblogsbackend.common.exception.BusinessException;
import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import com.cyfy.cyblogsbackend.common.tools.EncipherUtils;
import com.cyfy.cyblogsbackend.framework.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cy
 * @description 针对表【user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2025-02-21 21:46:22
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求参数
     * @return 新注册用户id
     */
    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        // 校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userAccount.length() > 25) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过长");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (userPassword.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过长");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 检查账号是否已被注册
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册账号已存在");
        }
        // 密码加密
        String encryptPassword = EncipherUtils.hashPsd(userPassword);
        // 插入数据
        UserInfo userInfo = new UserInfo();
        userInfo.setUserAccount(userAccount);
        userInfo.setUserPassword(encryptPassword);
        userInfo.setUserName("临时名");
        userInfo.setUserRole("USER");

        boolean saveResult = this.save(userInfo);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册失败,数据库错误");
        }
        return userInfo.getUserId();
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求参数
     * @param request
     * @return 存有脱敏后的用户信息的token令牌
     */
    @Override
    public String userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        // 校验
        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4
                || userPassword.length() < 8
                || userAccount.length() > 25
                || userPassword.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码错误");
        }

        // 查询用户是否存在
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        UserInfo user = this.getOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_EXIST);
        }
        // 校验密码
        if (!EncipherUtils.checkPsd(userPassword, user.getUserPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        // 记录用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user.getUserId());
        // 转换成封装类并转换为令牌
        LoginUserVO loginUserVO = this.getLoginUserVO(user);

        return jwtUtil.createToken(loginUserVO);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public LoginUserVO getCurrentLoginUser(HttpServletRequest request) {
        // 从session中获取用户id，用于校验令牌合法性
        Object userIdObject = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        Long userId = (Long) userIdObject;
        // 如果当前连接的session中存储的用户id
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }

        // 获取前端请求头中的X-Token数据
        String token = request.getHeader(UserConstant.TOKEN_KEY);
        if (StrUtil.isBlank(token)) {
            throw new BusinessException(ErrorCode.TOKEN_ERROR);
        }
        LoginUserVO currentUser;
        try {
            // 将token转换成对象
            currentUser = jwtUtil.parseToken(token, LoginUserVO.class);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_ERROR, "令牌已过期，请重新登录");
        } catch (MalformedJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_ERROR, "无效令牌，请重新登录");
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.TOKEN_ERROR);
        }
        // 判断当前用户id是否与token中的用户id一致
        if (!currentUser.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "非法登录");
        }
        return currentUser;
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        // 先判断用户是否登录
        Object userIdObject = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (userIdObject == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    /**
     * 用户信息脱敏处理
     *
     * @param userInfo
     * @return
     */
    @Override
    public LoginUserVO getLoginUserVO(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(userInfo, loginUserVO);
        return loginUserVO;
    }
}




