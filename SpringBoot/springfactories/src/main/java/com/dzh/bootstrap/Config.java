package com.dzh.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${info.name}")
    private String name;

    @Value("${info.class}")
    private String classNum;

    public void show(){
        System.out.println(name);
        System.out.println(classNum);
    }
}
