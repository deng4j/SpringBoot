package com.example.importbeandefinitionregistrar;

import com.dzh.importbeandefinitionregistrar.demo.bean.BeanA;
import com.dzh.importbeandefinitionregistrar.demo.bean.BeanB;
import com.dzh.importbeandefinitionregistrar.demo.bean.BeanC;
import com.dzh.importbeandefinitionregistrar.main.ImportBeanDefinitionRegistrarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ImportBeanDefinitionRegistrarApplication.class)
class ImportBeanDefinitionRegistrarApplicationTests {

    @Autowired
    private BeanA beanA;
    @Autowired
    private BeanB beanB;

    @Autowired
    private BeanC beanC;

    @Test
    void contextLoads() {
        beanA.show();
        beanB.show();
        beanC.show();
    }

}
