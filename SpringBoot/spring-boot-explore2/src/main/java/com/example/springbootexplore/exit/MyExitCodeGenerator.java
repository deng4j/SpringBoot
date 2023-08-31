package com.example.springbootexplore.exit;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

/**
 * 代码退出的时候可以用，在调用context.close();
 */
@Component
public class MyExitCodeGenerator implements ExitCodeGenerator {
 
    @Override
    public int getExitCode() {
        System.out.println("========= MyExitCodeGenerator =========");
        return 100;
    }
 
}