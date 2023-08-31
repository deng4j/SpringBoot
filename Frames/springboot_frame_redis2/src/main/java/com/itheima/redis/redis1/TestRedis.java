package com.itheima.redis.redis1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * - ValueOperations：简单K-V操作
 * - SetOperations：set类型数据操作
 * - ZSetOperations：zset类型数据操作
 * - HashOperations：针对hash类型的数据操作
 * - ListOperations：针对list类型的数据操作
 */
@Component
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    public void testFolder(){
        redisTemplate.opsForValue().set("F:F1","张三");
        redisTemplate.opsForValue().set("F:F2","李四");

    }

    public void testRedisString(){
        //存
        redisTemplate.opsForValue().set("uname","张三");

        //取
        Object uname = redisTemplate.opsForValue().get("uname");
        System.out.println(uname);

        //存,并设置过期时间
        redisTemplate.opsForValue().set("city","郴州",100, TimeUnit.SECONDS);

        //存，如果存在了，不进行操作
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("city", "长沙");
        System.out.println(aBoolean);
    }

    public void testHash(){
        HashOperations hashOperations = redisTemplate.opsForHash();

        //存
        hashOperations.put("hash1","name","张三");
        hashOperations.put("hash1","age","18");
        hashOperations.put("hash1","address","郴州");

        //取
        Object name = hashOperations.get("hash1", "name");
        System.out.println(name);

        //获取hash所有字段
        Set keys = hashOperations.keys("hash1");
        for (Object key : keys) {
            System.out.println(key);
        }

    }

    public void  testList(){
        ListOperations listOperations = redisTemplate.opsForList();

        //存
        listOperations.leftPush("myList","b");
        listOperations.leftPush("myList","a");
        listOperations.rightPushAll("myList",1,2,3);

        //取
        List myList = listOperations.range("myList", 0, -1);
        for (Object list : myList) {
            System.out.println(list);
        }

        //获取列表长度
        Long size = listOperations.size("myList");
        System.out.println(size);
        //出队列
        for (int i = 0; i < size; i++) {
            Object rightPop = listOperations.rightPop("myList");
            System.out.println(rightPop);
        }

    }

    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();
        //存
        setOperations.add("mySet","a","b","c",1,2,3);
        //删除
        setOperations.remove("mySet","c",3);
        //取
        Set mySet = setOperations.members("mySet");
        for (Object set : mySet) {
            System.out.println(set);
        }
    }

    public void testZSet(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        //存
        zSetOperations.add("myZSet","a",1.0);
        zSetOperations.add("myZSet","b",1.2);
        zSetOperations.add("myZSet","c",1.6);
        zSetOperations.add("myZSet","e",3);
        zSetOperations.add("myZSet","d",4);
        zSetOperations.add("myZSet","d",2);

        //修改分数
        zSetOperations.incrementScore("myZSet","e",4);

        //取
        Set myZSet = zSetOperations.range("myZSet", 0, -1);
        for (Object zset : myZSet) {
            System.out.println(zset);
            //删除
            zSetOperations.remove("myZSet",zset);
        }

    }

    public void testCommon(){
        //获取所有key
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);
        //判断是否存在该key
        Boolean hasKey = redisTemplate.hasKey("name");
        System.out.println(hasKey);

        //删除key
        redisTemplate.delete("name");
        //获取key对应的value的数据类型
        DataType dataType = redisTemplate.type("ZSet");
        System.out.println(dataType);
    }
}
