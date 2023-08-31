package com.itheima.dao;

import com.itheima.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<Users,Integer> {

}
