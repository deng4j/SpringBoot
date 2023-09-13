package com.dzh.importbeandefinitionregistrar.main;

import com.dzh.importbeandefinitionregistrar.demo.EnableImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableImport
public class ImportBeanDefinitionRegistrarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportBeanDefinitionRegistrarApplication.class, args);
    }

}
