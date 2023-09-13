package com.example.dynamicimportbeandefinitionregistrar.demo;

import java.lang.annotation.*;

/**
 * 我们会把添加了该注解的类作为bean
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface BeanIoc {
}