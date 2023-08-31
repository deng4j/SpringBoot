package com.itheima.service.impl;

import com.itheima.bootdb1.mapper.UsersMapper;
import com.itheima.pojo.Users;
import com.itheima.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public void addUser(Users users) {
        usersMapper.addUser(users);
    }
}
