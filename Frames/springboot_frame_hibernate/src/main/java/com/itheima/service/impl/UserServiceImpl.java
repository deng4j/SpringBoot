package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.pojo.Users;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 方法使用hibernate自带方法
     */
    @Override
    public void addUser(Users users) {
        userDao.save(users);
    }

    @Override
    public List<Users> findAll() {
        List<Users> usersList = userDao.findAll();
        return usersList;
    }
}
