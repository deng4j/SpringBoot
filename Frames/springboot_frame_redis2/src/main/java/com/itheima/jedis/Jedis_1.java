package com.itheima.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Set;

public class Jedis_1 {

    public void testJedis(){
        //1.获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //2.执行操作
        //Sting
        System.out.println("String_________________________");
        jedis.set("uname","张三");

        String uname = jedis.get("uname");
        System.out.println(uname);

        System.out.println("hash_________________________");
        //Hash
        jedis.hset("h1","uname","李四");
        String hget = jedis.hget("h1", "uname");
        System.out.println(hget);

        System.out.println("keys_________________________");
        //获取所有key
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

        //关闭连接
        jedis.close();
    }

    /**
     * redis事务：
     *  隔离性：事务的所有命令都会序列化、按顺序的执行，事务执行完后才会执行其他客服端的命令。
     *  原子性： 事务中的命令要么全部被执行，要么全部不执行，没有回滚。
     *
     * 遇到错误：
     *  入队时出错，一般时因为语法错误引起的，加入事务队列就会报错，遇到这类错误，一般会放弃事务。
     *  EXEC调用后出错，即使某个命令产生了错误，其他命令依旧会继续执行执行
     */
    public void testMulti(Integer age){
        Jedis jedis = new Jedis("localhost", 6379);

        // 开启事务
        Transaction transaction = jedis.multi();

        transaction.set("uname","张三");
        transaction.set("age",age.toString());

        if (age>0&&age<200){
            // 提交事务
            transaction.exec();
        }else {
            // 放弃事务
            transaction.discard();
        }
        //关闭连接
        jedis.close();
    }

    /**
     * WATCH命令:WATCH 命令可以监控键，如果被监控的键，再 EXEC 之前被修改，那么事务会放弃执行。
     * EXEC 执行以后，无论事务是否执行成功，都会放弃对所有键的监控。
     */
    public void testWATCH(Integer age){
        Jedis jedis = new Jedis("localhost", 6379);

        jedis.set("uname","张三");

        // 监控键
        jedis.watch("uname");
        if (age<0||age>200){
            // 放弃所有被监控的键
            jedis.unwatch();
        }
        jedis.set("uname","李四");

        // 开启事务
        Transaction transaction = jedis.multi();
        transaction.set("uname","王五");
        transaction.set("age",age.toString());
        if (age>0&&age<200){
            // 提交事务
            transaction.exec();
        }else {
            // 放弃事务
            transaction.discard();
        }

        //关闭连接
        jedis.close();
    }


    public void testFolder(){
        Jedis jedis = new Jedis("localhost", 6379);

        jedis.set( "userinfo:shanghai",  "张三");
        jedis.set( "userinfo:guangzhou", "李四");

        jedis.close();
    }
}
