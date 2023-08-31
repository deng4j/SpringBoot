package com.example.springbootexplore.conditional.conditionalOnExpression;

import com.example.springbootexplore.conditional.conditionalOnExpression.service.OnExpService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpEL条件达成才生效
 */
@Configuration
@ConditionalOnExpression("false")
public class ExpConfig {
 
    @Bean
    public OnExpService onExpService() {
        return new OnExpService();
    }

}