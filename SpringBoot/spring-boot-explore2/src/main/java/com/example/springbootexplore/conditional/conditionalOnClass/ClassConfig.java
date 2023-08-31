package com.example.springbootexplore.conditional.conditionalOnClass;

import com.example.springbootexplore.conditional.conditionalOnClass.service.OnClassService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 有该路径上的class才生效
 */
@Configuration
@ConditionalOnClass(name = {"com.example.springbootexplore.conditional.conditionalOnClass.ConditionClass"})
public class ClassConfig {
 
    @Bean
    public OnClassService onClassService() {
        return new OnClassService();
    }

}