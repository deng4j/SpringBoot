package com.itheima;

import com.itheima.pojo.Users;
import com.itheima.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

}
