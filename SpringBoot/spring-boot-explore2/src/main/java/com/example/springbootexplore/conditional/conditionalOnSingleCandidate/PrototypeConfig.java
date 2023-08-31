package com.example.springbootexplore.conditional.conditionalOnSingleCandidate;

import com.example.springbootexplore.conditional.conditionalOnSingleCandidate.service.PrototypeService;
import com.example.springbootexplore.conditional.conditionalOnSingleCandidate.service.SingleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrototypeConfig {

    @Bean("prototypeService1")
    public PrototypeService prototypeService1() {
        return new PrototypeService();
    }

    @Bean("prototypeService2")
    public PrototypeService prototypeService2() {
        return new PrototypeService();
    }
}
