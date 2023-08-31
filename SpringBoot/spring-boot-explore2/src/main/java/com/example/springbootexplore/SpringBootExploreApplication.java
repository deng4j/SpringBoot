package com.example.springbootexplore;

import com.example.springbootexplore.event.test2.MyApplicationEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootExploreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExploreApplication.class, args)
            .publishEvent(new MyApplicationEvent(new Object())); //发消息
    }
}
