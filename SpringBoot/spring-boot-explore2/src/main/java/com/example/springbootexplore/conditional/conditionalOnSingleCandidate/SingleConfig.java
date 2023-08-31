package com.example.springbootexplore.conditional.conditionalOnSingleCandidate;

import com.example.springbootexplore.conditional.conditionalOnSingleCandidate.service.PrototypeService;
import com.example.springbootexplore.conditional.conditionalOnSingleCandidate.service.SingleService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *  该bean是个单例才生效
 */
@Configuration
@ConditionalOnSingleCandidate(PrototypeService.class)
public class SingleConfig {
 
    @Bean
    public SingleService singleService() {
        return new SingleService();
    }

}