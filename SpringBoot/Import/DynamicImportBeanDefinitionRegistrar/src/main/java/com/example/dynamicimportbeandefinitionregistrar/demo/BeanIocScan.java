package com.example.dynamicimportbeandefinitionregistrar.demo;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 定义包路径。(指定包下所有添加了BeanIoc注解的类作为bean)
 * 注意这里 @Import(BeanIocScannerRegister.class) 的使用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(BeanIocScannerRegister.class)
public @interface BeanIocScan {
    String[] basePackages() default "";
}