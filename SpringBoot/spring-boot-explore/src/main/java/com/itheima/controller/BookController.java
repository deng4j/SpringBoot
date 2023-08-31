package com.itheima.controller;


import com.itheima.domain.Enterprise;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自动配置：该注解会使SpringBoot根据项目依赖的jar自动配置项目的配置项。
 * 例如：添加量spring-boot-starter-web的依赖，项目就会引入SpringMvc的依赖，
 * springBoot就会配置Tomcat和SpringMVC。
 */
//@EnableAutoConfiguration(exclude = BookService.class)

@RestController
@RequestMapping("/books")
public class BookController {

    @Value("${server.port}")
    private String port;

    @Value("${user.name}")
    private String name;
    @Value("${user.age}")
    private String age;

    @Autowired
    private Environment evn;

    @Autowired
    private Enterprise enterprise;

    @GetMapping("/{id}")
    public String getAll(@PathVariable Integer id) {
        System.out.println(id);
        return "获取全部";
    }

    @GetMapping("/data")
    public String getData() {
        String ports = evn.getProperty("server.port");
        System.out.println("ports:" + ports);
        System.out.println("username:" + name + " userAge:" + age);

        String enname = evn.getProperty("enterprise.name");
        String sub = evn.getProperty("enterprise.subject[0]");
        System.out.println("enName:" + enname + " sub1:" + sub);

        return enterprise.toString();
    }
}
