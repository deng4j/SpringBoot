package com.itheima.controller;

import com.itheima.pojo.Users;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("jpa")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/addUser")
    public String addUser(){
        Users users = new Users();
        users.setName("孙悟空");
        users.setPassword("123456");
        users.setEmail("846@qq.com");
        users.setBirthday(new Date(1987,5,20));
        userService.addUser(users);
        return "ok";
    }

    @GetMapping("/findAll")
    public String findAll(){
        List<Users> usersList = userService.findAll();
        return usersList.toString();
    }
}
