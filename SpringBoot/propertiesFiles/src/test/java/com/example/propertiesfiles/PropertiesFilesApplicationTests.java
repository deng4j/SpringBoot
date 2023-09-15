package com.example.propertiesfiles;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PropertiesFilesApplicationTests {
    @Autowired
    private Config config;

    @Test
    void contextLoads() {
        config.show();
    }

}
