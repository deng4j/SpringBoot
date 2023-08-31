package com.itheima.service;

import com.itheima.pojo.Users;

import java.util.List;

public interface UserService {
    void  addUser(Users users);
    List<Users> findAll();
}
