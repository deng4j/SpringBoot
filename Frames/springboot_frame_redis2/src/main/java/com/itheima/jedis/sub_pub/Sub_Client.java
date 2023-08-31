package com.itheima.jedis.sub_pub;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sub_Client {

    public static void main(String[] args) {

        ExecutorService es= Executors.newCachedThreadPool();
        es.submit(()->{
            client_sub();
            client_unsub();
        });
        es.submit(()->{
            client_psub();
        });
    }

    public static void client_sub(){
        Jedis jedis = new Jedis("localhost", 6379);

        try {
            jedis.subscribe(new MyJedisPubSub(),"Channel-Chat");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static void client_psub(){
        Jedis jedis = new Jedis("localhost", 6379);

        try {
            jedis.psubscribe(new MyJedisPubSub(),"topic*");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static void client_unsub(){
        Jedis jedis = new Jedis("localhost", 6379);

        try {
            //取消订阅
            new MyJedisPubSub().onUnsubscribe("Channel-Chat",1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
