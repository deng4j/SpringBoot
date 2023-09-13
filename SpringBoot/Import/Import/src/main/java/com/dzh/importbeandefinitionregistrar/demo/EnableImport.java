package com.dzh.importbeandefinitionregistrar.demo;

import com.dzh.importbeandefinitionregistrar.demo.bean.BeanB;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * ImportBeanDefinitionRegistrar类注入，和普通类注入
 */
@Import({StaticImportBeanDefinitionRegistrar.class,BeanB.class, StaticConfigurationSelector.class})
public @interface EnableImport {
}
