package com.example.dynamicimportbeandefinitionregistrar.demo;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 搜索指定包下所有添加了BeanIoc注解的类，并且把这些类添加到ioc容器里面去
 */
public class BeanIocScannerRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private final static String PACKAGE_NAME_KEY = "basePackages";

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //1. 从BeanIocScan注解获取到我们要搜索的包路径
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(BeanIocScan.class.getName()));
        if (annoAttrs == null || annoAttrs.isEmpty()) {
            return;
        }
        String[] basePackages = (String[]) annoAttrs.get(PACKAGE_NAME_KEY);
        // 2. 找到指定包路径下所有添加了BeanIoc注解的类，并且把这些类添加到IOC容器里面去
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry, false);
        scanner.setResourceLoader(resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(BeanIoc.class));
        scanner.scan(basePackages);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}