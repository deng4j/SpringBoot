package com.itheima.bootdb1.mapper;

import com.itheima.pojo.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("bootdb1SqlSessionFactory")
@Mapper
public interface UsersMapper {

    @Insert("INSERT into users (name,password,email,birthday) VALUES " +
            "(#{name},#{password},#{email},#{birthday});")
    void addUser(Users users);
}
