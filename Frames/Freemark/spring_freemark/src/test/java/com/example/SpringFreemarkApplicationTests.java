package com.example;

import com.example.domain.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootTest(classes = SpringFreemarkApplication.class)
@RunWith(SpringRunner.class)
class SpringFreemarkApplicationTests {

    @Autowired
    private Configuration configuration;

    /**
     * 输出文件
     */
    @Test
    void test() throws Exception {
        Template template = configuration.getTemplate("02-list.ftl");

        template.process(getData(),new FileWriter("D:\\window\\temp\\list.html"));
    }

    private Map getData() {
        HashMap<String, Object> model = new HashMap<>();

        Student stu1 = new Student();
        stu1.setName("小强");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());

        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);

        //将两个对象模型数据存放到List集合中
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);

        //向model中存放List集合数据
        model.put("stus",stus);

        HashMap<String, Object> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);

        model.put("stuMap", stuMap);
        return model;
    }
}
