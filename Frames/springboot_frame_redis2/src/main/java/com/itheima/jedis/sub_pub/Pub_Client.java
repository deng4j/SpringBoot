package com.itheima.jedis.sub_pub;

import redis.clients.jedis.Jedis;

public class Pub_Client {

    public static void main(String[] args) {
        client_pub();
    }

    public static void client_pub(){
        Jedis jedis = new Jedis("localhost", 6379);

        try {
            jedis.publish("Channel-Chat","hello what fuck");
            jedis.publish("topic-XXX","hello what fuck");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
