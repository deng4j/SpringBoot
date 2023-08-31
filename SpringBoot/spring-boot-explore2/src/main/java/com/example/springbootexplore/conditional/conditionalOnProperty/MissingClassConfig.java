package com.example.springbootexplore.conditional.conditionalOnProperty;

import com.example.springbootexplore.conditional.conditionalOnProperty.service.MissingClassService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  没有该路径的class才生效
 */
@Configuration
@ConditionalOnMissingClass("com.example.springbootexplore.conditional.conditionalOnMissingClass.A")
public class MissingClassConfig {
 
    @Bean
    public MissingClassService missingClassService() {
        return new MissingClassService();
    }

}