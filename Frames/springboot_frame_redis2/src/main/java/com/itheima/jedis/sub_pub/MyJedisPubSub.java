package com.itheima.jedis.sub_pub;

import redis.clients.jedis.JedisPubSub;

public class MyJedisPubSub extends JedisPubSub {
    //接收到消息时执行
    @Override
    public void  onMessage(String channel, String message){
        System.out.println("oneJedisPubSub message is：" + message);
    }

    //接收到模式消息时执行
    @Override
    public void onPMessage(String pattern, String channel, String message){
        System.out.println("oneJedisPubSub pattern是："+pattern+" channel是："+channel + " message是" + message);
    }

    //订阅时执行
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("oneJedisPubSub订阅成功");
    }

    //取消订阅时执行
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels){
        System.out.println("oneJedisPubSub取消订阅："+channel);
    }


    //取消模式订阅时执行
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println("oneJedisPubSub取消多订阅："+pattern);
    }
}
