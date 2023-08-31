package com.itheima.bootdb2.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("bootdb2SqlSessionFactory")
@Mapper
public interface UserMapper {

    @Insert("INSERT into user (username,birthday,sex,address) VALUES " +
            "(#{username},#{birthday},#{sex},#{address});")
    void addUser(User user);
}
