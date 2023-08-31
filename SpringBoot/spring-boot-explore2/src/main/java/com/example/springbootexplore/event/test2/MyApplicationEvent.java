package com.example.springbootexplore.event.test2;

import org.springframework.context.ApplicationEvent;

public class MyApplicationEvent extends ApplicationEvent {

    public MyApplicationEvent(Object source) {
        super(source);
    }

    public  void printEventMessage(String message){
        System.out.println("监听到的事件信息:"+message);
    }
}