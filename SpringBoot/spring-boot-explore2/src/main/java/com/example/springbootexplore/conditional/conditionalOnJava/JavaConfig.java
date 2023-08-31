package com.example.springbootexplore.conditional.conditionalOnJava;

import com.example.springbootexplore.conditional.conditionalOnJava.service.JavaService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  JVM 版本进行匹配
 */
@Configuration
@ConditionalOnJava(value = JavaVersion.EIGHT,
    range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
public class JavaConfig {
 
    @Bean
    public JavaService javaService() {
        return new JavaService();
    }

}