package com.example.dynamicselectimport.demo;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicSelectImport implements ImportSelector {
    /**
     * DynamicSelectImport需要配合@Import()注解使用
     * <p>
     * 通过其参数AnnotationMetadata importingClassMetadata可以获取到@Import标注的Class的各种信息，
     * 包括其Class名称，实现的接口名称、父类名称、添加的其它注解等信息，通过这些额外的信息可以辅助我们选择需要定义为Spring bean的Class名称
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 第一步：获取到通过ComponentScan指定的包路径
        String[] basePackages = null;
        // @Import注解对应的类上的ComponentScan注解
        if (importingClassMetadata.hasAnnotation(ComponentScan.class.getName())) {
            Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(ComponentScan.class.getName());
            basePackages = (String[]) annotationAttributes.get("basePackages");
        }
        if (basePackages == null || basePackages.length == 0) {
            //ComponentScan的basePackages默认为空数组
            String basePackage = null;
            try {
                // @Import注解对应的类的包名
                basePackage = Class.forName(importingClassMetadata.getClassName()).getPackage().getName();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            basePackages = new String[]{basePackage};
        }
        // 第er步，知道指定包路径下所有 实现了HelloService接口的类 （ClassPathScanningCandidateComponentProvider的使用）
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        TypeFilter helloServiceFilter = new AssignableTypeFilter(HelloService.class);
        scanner.addIncludeFilter(helloServiceFilter);
        Set<String> classes = new HashSet<>();
        for (String basePackage : basePackages) {
            scanner.findCandidateComponents(basePackage).forEach(beanDefinition -> classes.add(beanDefinition.getBeanClassName()));
        }
        // 第三步，返回添加到IOC容器里面去
        return classes.toArray(new String[0]);
    }
}