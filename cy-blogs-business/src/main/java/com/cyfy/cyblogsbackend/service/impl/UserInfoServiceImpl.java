package com.cyfy.cyblogsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyfy.cyblogsbackend.domain.UserInfo;
import com.cyfy.cyblogsbackend.service.UserInfoService;
import com.cyfy.cyblogsbackend.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @author cy
 * @description 针对表【user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2025-02-21 21:46:22
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

}




