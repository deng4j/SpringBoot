package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.Users;

import java.util.List;

public interface UserService extends IService<Users> {
    List<Users> findAll(Users users);

    Users saveUsers(Users users);

    void  deleteByid(String id);

    void update(Users users);

    Users selectById(String id);
}
