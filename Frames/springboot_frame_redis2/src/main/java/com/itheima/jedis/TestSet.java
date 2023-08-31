package com.itheima.jedis;

import redis.clients.jedis.Jedis;

public class TestSet {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.flushDB();
        System.out.println("============向集合中添加元素(不重复)============");
        System.out.println(jedis.sadd("eleset","e1","e2","e4","e3","ee","e8","e7","e5"));
        System.out.println(jedis.sadd("eleSet","e6"));
        System.out.println(jedis.sadd("eleSet","e6"));
        System.out.println("eleSet的所有元素为:"+jedis.smembers("eleSet"));
        System.out.println("删除一个元素ee:"+jedis.srem("eleSet","ee"));
        System.out.println("eleSet的所有元素为:"+jedis.smembers("eleSet"));
        System.out.println("删除两个元素e7和e6:"+jedis.srem("eleset","e7","e6"));
        System.out.println("eleSet的所有元素为:"+jedis.smembers("eleSet"));
        System.out.println("随机的移除集合中的一个元素:"+jedis.spop("eleSet"));
        System.out.println("随机的移除集合中的一个元素:"+jedis.spop("eleSet"));
        System.out.println("eleSet的所有元素为:"+jedis.smembers("eleSet"));
        System.out.println("eleSet中包含元素的个数:"+jedis.scard("eleSet"));
        System.out.println("e3是否在eleSet中:"+jedis.sismember("eleSet","e3"));
        System.out.println("e1是否在eleSet中:"+jedis.sismember("eleSet","e1"));
        System.out.println("e1是否在eleSet中:"+jedis.sismember("eleSet","e5"));
        
        System.out.println("=================================");
        System.out.println(jedis.sadd("eleSet1","e1","e2","e4","e3","ee","e8","e7","e5"));
        System.out.println(jedis.sadd("eleSet2","e1","e2","e4","e3","ee","e8"));
        System.out.println("将eleSet1中删除el并存入eleSet3中:"+jedis.smove("eleSet1","eleSet3","e1"));
        System.out.println("将eleSet1中删除e2并存入eleSet3中:"+jedis.smove("eleSet1","eleSet3","e2"));
        System.out.println("eleSet1中的元素:"+jedis.smembers("eleSet1"));
        System.out.println("eleSet3中的元素:"+jedis.smembers("eleSet3"));
        System.out.println("============集合运算=================");
        System.out.println("eleSet1中的元素:"+jedis.smembers("eleSet1"));
        System.out.println("eleSet2中的元素:"+jedis.smembers("eleSet2"));
        System.out.println("eleSet1和eleSet2的交集："+jedis.sinter("eleSet1","eleSet2"));
        System.out.println("eleSet1和eleSet2的并集："+jedis.sunion("eleSet1","eleSet2"));
        System.out.println("eleSet1和eleSet2的差集："+jedis.sdiff("eleSet1","eleSet2"));//eleSet1中有,eleSet2
        jedis.sinterstore("eleSet4","eleSet1","eleSet2");//求交集并将交集保存到dstkey的集合
        System.out.println("eleset4中的元素："+jedis.smembers("eleSet4"));
    }
}
