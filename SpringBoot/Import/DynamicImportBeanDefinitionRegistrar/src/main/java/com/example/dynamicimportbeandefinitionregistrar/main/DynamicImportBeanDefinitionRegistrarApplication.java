package com.example.dynamicimportbeandefinitionregistrar.main;

import com.example.dynamicimportbeandefinitionregistrar.demo.BeanIocScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@BeanIocScan(basePackages = "com.example.dynamicimportbeandefinitionregistrar.demo")
public class DynamicImportBeanDefinitionRegistrarApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicImportBeanDefinitionRegistrarApplication.class, args);
    }

}
