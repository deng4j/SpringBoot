package com.example.propertiesfiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${server.port}")
    private String port;

    @Value("${user.name}")
    private String name;

    @Value("${user.age}")
    private String age;

    public void show(){
        System.out.println(port);
        System.out.println(name);
        System.out.println(name);
    }
}
