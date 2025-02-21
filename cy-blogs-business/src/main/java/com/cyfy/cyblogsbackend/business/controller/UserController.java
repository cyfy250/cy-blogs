package com.cyfy.cyblogsbackend.business.controller;

import com.cyfy.cyblogsbackend.business.domain.UserInfo;
import com.cyfy.cyblogsbackend.business.service.UserInfoService;
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
    public String add(@RequestBody UserInfo userInfo) {
        userInfoService.save(userInfo);
        return "success";
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
