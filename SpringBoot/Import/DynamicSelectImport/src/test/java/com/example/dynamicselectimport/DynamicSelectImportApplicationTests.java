package com.example.dynamicselectimport;

import com.example.dynamicselectimport.demo.HelloService;
import com.example.dynamicselectimport.main.DynamicSelectImportApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DynamicSelectImportApplication.class)
class DynamicSelectImportApplicationTests {
    @Autowired
    private HelloService helloService;

    @Test
    void contextLoads() {
        System.out.println(helloService);
    }

}
