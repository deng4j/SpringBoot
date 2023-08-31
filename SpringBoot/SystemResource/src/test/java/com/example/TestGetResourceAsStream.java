package com.example;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestGetResourceAsStream {

    @Test
    public void test() throws IOException {
        File file = new File("src/main/resources/folder/a.txt");
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        System.out.println(file.getPath());
        System.out.println(content);
    }


    @Test
    public void test1() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("folder/a.txt");
        String result = IOUtils.toString(in, StandardCharsets.UTF_8);
        System.out.println(result);
    }

    @Test
    public void test2() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("folder/a.txt");
        String content = FileUtils.readFileToString(new File(url.getPath()), StandardCharsets.UTF_8);
        System.out.println(url.getPath());
        System.out.println(content);
    }

    /**
     * getClass要加/
     */
    @Test
    public void test3() throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/folder/a.txt");
        String result = IOUtils.toString(in, StandardCharsets.UTF_8);
        System.out.println(result);
    }

    @Test
    public void test4() throws IOException {
        URL url = this.getClass().getResource("/folder/a.txt");
        String content = FileUtils.readFileToString(new File(url.getPath()), StandardCharsets.UTF_8);
        System.out.println(url.getPath());
        System.out.println(content);
    }


    @Test
    public void test5() throws IOException {
        File file = ResourceUtils.getFile("classpath:folder/a.txt");
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        System.out.println(file.getPath());
        System.out.println(content);
    }

    @Test
    public void test6() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("folder/a.txt");
        InputStream inputStream = classPathResource.getInputStream();
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        System.out.println(classPathResource.getURL().getPath());
        System.out.println(classPathResource.getURI().getPath());
        System.out.println(content);
    }




    @Test
    public void test7(){
        URL url = this.getClass().getClassLoader().getResource("");
        System.out.println(url.getPath());
    }

    @Test
    public void test8(){
        URL url = this.getClass().getResource("");
        System.out.println(url.getPath());

        URL url2 = this.getClass().getResource("/");
        System.out.println(url2);
    }

    @Test
    public void test9(){
        URL url = ClassLoader.getSystemResource("");
        System.out.println(url.getPath());
    }

    @Test
    public void test10(){
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println(url.getPath());

        String path = System.getProperty("java.class.path");
        System.out.println(path);
    }
}
