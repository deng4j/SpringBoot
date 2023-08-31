package com.itheima.jedis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class TestHash {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.flushDB();
        Map<String,String> map=new HashMap<>();
        map.put("1","valuel");map.put("2","value2");
        map.put("3","value3");map.put("4","value4");
        //添加名称为hash()的hash元素
        jedis.hmset("hash",map);
        //向名称为hash的hash中添加为5,value为value5元素
        jedis.hset("hash","5","value5");
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));//return Map<String,String>
        System.out.println("散列hash的所有键为："+jedis.hkeys("hash"));//return Set<String>
        System.out.println("散列hash的所有值为："+jedis.hvals("hash"));//return List<string>
        System.out.println("将6保存的值加上一个整数,如果6不存在则添加6："+jedis.hincrBy("hash","6",6));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("将6保存的值加上一个整数,如果6不存在则添加6："+jedis.hincrBy("hash","6",6));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("删除一个或者多个键值对："+jedis.hdel("hash","2"));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("散列hash中键值对的个数："+jedis.hlen("hash"));
        System.out.println("判断hash中是否存在2："+jedis.hexists("hash","2"));
        System.out.println("判断hash中是否存在3："+jedis.hexists("hash","3"));
        System.out.println("获取hash中的值："+jedis.hmget("hash","3"));
        System.out.println("获取hash中的值："+jedis.hmget("hash","3","4"));
    }
}
