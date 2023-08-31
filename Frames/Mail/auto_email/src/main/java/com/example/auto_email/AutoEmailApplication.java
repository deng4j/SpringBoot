package com.example.auto_email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.Arrays;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AutoEmailApplication {

    public static void main(String[] args) {
        System.out.println("\033[32m" + "------------"+Arrays.toString(args)+"------------" + "\033[0m");
        SpringApplication.run(AutoEmailApplication.class, args);
    }

}
