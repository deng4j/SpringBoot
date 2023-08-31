package com.itheima.controller;

import com.itheima.pojo.Users;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> findAll(Users users){
        List<Users> usersList = userService.findAll(users);
        return usersList;
    }

    @GetMapping("/{id}")
    public Users selectById(@PathVariable String id){
        Users users= userService.selectById(id);
        return users;
    }

    @PostMapping
    public Users saveUsers(@RequestBody Users users) throws InterruptedException {

        Users saveUsers = userService.saveUsers(users);
        return saveUsers;
    }

    @DeleteMapping("/{id}")
    public void  deleteByid(@PathVariable String id){
        userService.deleteByid(id);
    }

    @PutMapping
    public Users update(Users users){
        userService.update(users);
        return users;
    }
}
