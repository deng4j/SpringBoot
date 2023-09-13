package com.dzh.importbeandefinitionregistrar.demo;

import com.dzh.importbeandefinitionregistrar.demo.bean.BeanA;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Spring官方在动态注册bean时，大部分套路其实是使用ImportBeanDefinitionRegistrar接口。
 *
 * 所有实现了该接口的类的都会被ConfigurationClassPostProcessor处理
 *
 * 静态注入案例
 */
public class StaticImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName(BeanA.class.getName());
        registry.registerBeanDefinition("beanA", beanDefinition);
    }
}