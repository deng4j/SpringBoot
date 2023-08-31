package com.example.springbootexplore.conditional.conditionalOnMissingClass;

import com.example.springbootexplore.conditional.conditionalOnMissingClass.service.OnPropertyService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  配置文件有符合该属性的配置才生效
 */
@Configuration
@ConditionalOnProperty(prefix = "pro.service", name = "user.enable",havingValue = "false")
public class OnPropertyConfig {
 
    @Bean
    public OnPropertyService onPropertyService() {
        return new OnPropertyService();
    }

}