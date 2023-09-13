package com.example.dynamicimportbeandefinitionregistrar;

import com.example.dynamicimportbeandefinitionregistrar.demo.BeanA;
import com.example.dynamicimportbeandefinitionregistrar.main.DynamicImportBeanDefinitionRegistrarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DynamicImportBeanDefinitionRegistrarApplication.class)
class DynamicImportBeanDefinitionRegistrarApplicationTests {
    @Autowired
    private BeanA beanA;

    @Test
    void contextLoads() {
        System.out.println(beanA);
    }

}
