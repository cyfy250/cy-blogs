package com.cyfy.cyblogsbackend.business.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfoVO implements Serializable {
    /**
     * 用户表主键
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户邮件
     */
    private String userEmail;

    /**
     * 用户头像
     */
    private String userAvatar;


    private static final long serialVersionUID = 1L;
}
