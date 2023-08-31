package com.example.springbootexplore.conditional.condition;

import com.example.springbootexplore.conditional.condition.service.Service1;
import com.example.springbootexplore.conditional.condition.service.Service2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(MyCondition.class)
public class MyConfig {
 
    @Bean("service1")
    public Service1 service1() {
        return new Service1();
    }

    @Bean("service2")
    public Service2 service2() {
        return new Service2();
    }
}