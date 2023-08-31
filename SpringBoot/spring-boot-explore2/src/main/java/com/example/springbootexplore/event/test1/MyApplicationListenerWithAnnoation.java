package com.example.springbootexplore.event.test1;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;


@Configuration
public class MyApplicationListenerWithAnnoation {

    @EventListener(classes = {ContextRefreshedEvent.class})
    protected void testEvent(){
        System.out.println("注解形式的事件刷新成功ContextRefreshedEvent");
    }

}
