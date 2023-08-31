package com.itheima;

import com.itheima.redis.redis1.TestRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RedisApplication.class, args);
        //String
        System.out.println("-----------String--------------");
        TestRedis bean = run.getBean(TestRedis.class);
        bean.testRedisString();
        System.out.println("-----------hash--------------");
        bean.testHash();
        System.out.println("-----------list--------------");
        bean.testList();
        System.out.println("-----------set--------------");
        bean.testSet();
        System.out.println("-----------zset--------------");
        bean.testZSet();
        System.out.println("-----------common--------------");
        bean.testCommon();

        bean.testFolder();
    }

}
