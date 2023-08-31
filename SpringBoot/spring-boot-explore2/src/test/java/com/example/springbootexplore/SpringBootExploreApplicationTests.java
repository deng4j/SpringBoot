package com.example.springbootexplore;

import com.example.springbootexplore.Import.ImportTest;
import com.example.springbootexplore.autoWired.IBaseService;
import com.example.springbootexplore.conditional.conditionalOnBean.service.UserService;
import com.example.springbootexplore.properties.ConfigurationPropertiesTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringBootExploreApplicationTests {

    @Autowired
    public ApplicationContext applicationContext;


    @Autowired
    private ImportTest importTest;

    @Autowired
    @Qualifier(value = "baseServiceImplA")
    private IBaseService baseService;

    @Autowired
    private ConfigurationPropertiesTest configurationPropertiesTest;



    @Test
    void importTest() {
        importTest.importTest();
    }

    @Test
    void autowiredTest() {
        baseService.getMsa();
    }

    @Test
    void conditionA_Test() {
        try {
            System.out.println(applicationContext.getBean("service1"));
        } catch (BeansException e) {
            System.out.println("service1：null");
        }
        try {
            System.out.println(applicationContext.getBean("service2"));
        } catch (BeansException e) {
            System.out.println("service2：null");
        }
    }

    @Test
    void conditionalOnBeanTest() {
        try {
            System.out.println(applicationContext.getBean("userService"));
        } catch (BeansException e) {
            System.out.println("userService：null");
        }
    }

    @Test
    void conditionalOnClassTest() {
        try {
            System.out.println(applicationContext.getBean("onClassService"));
        } catch (BeansException e) {
            System.out.println("onClassService：null");
        }
    }

    @Test
    void conditionalOnExpTest() {
        try {
            System.out.println(applicationContext.getBean("onExpService"));
        } catch (BeansException e) {
            System.out.println("onExpService：null");
        }
    }

    @Test
    void conditionalOnJavaTest() {
        try {
            System.out.println(applicationContext.getBean("javaService"));
        } catch (BeansException e) {
            System.out.println("javaService：null");
        }
    }

    @Test
    void conditionalOnMissClassTest() {
        try {
            System.out.println(applicationContext.getBean("missingClassService"));
        } catch (BeansException e) {
            System.out.println("missingClassService：null");
        }
    }

    @Test
    void conditionalOnPropertyTest() {
        try {
            System.out.println(applicationContext.getBean("onPropertyService"));
        } catch (BeansException e) {
            System.out.println("onPropertyService：null");
        }
    }

    @Test
    void conditionalOnResourceTest() {
        try {
            System.out.println(applicationContext.getBean("onResourceService"));
        } catch (BeansException e) {
            System.out.println("onResourceService：null");
        }
    }

    @Test
    void conditionalOnSingleCandidateTest() {
        try {
            System.out.println(applicationContext.getBean("singleService"));
        } catch (BeansException e) {
            System.out.println("singleService：null");
        }
    }

    @Test
    void ConfigurationPropertiesTest(){
        System.out.println(configurationPropertiesTest.getLocal());
    }
}
