package com.dzh.importbeandefinitionregistrar.demo;

import com.dzh.importbeandefinitionregistrar.demo.bean.BeanC;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportSelector一定要配合@Import使用
 *
 * 静态场景(注入已知的类)
 */
public class StaticConfigurationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{BeanC.class.getName()};
    }
}
