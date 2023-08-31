package com.example.springbootexplore.conditional.conditionalOnResource;

import com.example.springbootexplore.conditional.conditionalOnResource.service.OnResourceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  classpath路径有该资源才生效
 */
@Configuration
@ConditionalOnResource(resources = {"classpath:application.yml"})
public class OnResourceConfig {
 
    @Bean
    public OnResourceService onResourceService() {
        return new OnResourceService();
    }

}