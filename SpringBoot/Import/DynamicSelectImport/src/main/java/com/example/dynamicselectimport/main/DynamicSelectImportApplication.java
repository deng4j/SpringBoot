package com.example.dynamicselectimport.main;

import com.example.dynamicselectimport.demo.EnableImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.dynamicselectimport.demo") // 指定路径
@EnableImport
public class DynamicSelectImportApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicSelectImportApplication.class, args);
    }

}
