package com.itheima.controller;

import com.itheima.pojo.Users;
import com.itheima.service.UserService;
import com.itheima.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("us")
public class UsController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String addUser(){
        Users users = new Users();
        users.setName("卡不卡");
        users.setPassword("123456");
        users.setEmail("1546@qq.com");
        users.setBirthday(new Date(1987,5,20));

        usersService.addUser(users);
        return "success";
    }
}
