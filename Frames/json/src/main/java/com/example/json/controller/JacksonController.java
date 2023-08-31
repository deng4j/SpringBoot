package com.example.json.controller;

import com.alibaba.fastjson.JSON;
import com.example.json.domain.jackson.Jackson1Vo;
import com.example.json.domain.jackson.Jackson2Vo;
import com.example.json.domain.jackson.Jackson3Vo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("jackson")
public class JacksonController {


    @GetMapping("/jackson1")
    public ResponseEntity jackson1() {
        Jackson1Vo jackson1Vo = new Jackson1Vo();
        jackson1Vo.setId(100);
        jackson1Vo.setAa("world");
        jackson1Vo.setDate(new Date());
        return ResponseEntity.ok(jackson1Vo);
    }

    @GetMapping("/jackson2")
    public ResponseEntity jackson2() throws JsonProcessingException {
        Jackson2Vo jackson2Vo = new Jackson2Vo();
        jackson2Vo.setId(100);
        jackson2Vo.setName("黄飞鸿");
        jackson2Vo.setSex("男的");

        //1.配置过滤器,只保留属性c
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "sex");
        filterProvider.addFilter("empFilter", filter);
        //2.设置过滤器
        ObjectMapper om = new ObjectMapper();
        om.setFilterProvider(filterProvider);
        //3.添加
        String str = om.writeValueAsString(jackson2Vo);
        System.err.println(str);

        return ResponseEntity.ok(str);
    }

    @GetMapping("/jackson3")
    public ResponseEntity jackson3() throws JsonProcessingException {
        Jackson3Vo jackson3Vo = new Jackson3Vo();
        jackson3Vo.setName("孙悟空");
        jackson3Vo.setAls("行者孙");
        jackson3Vo.setHireDate(new Date());
        jackson3Vo.setAge(600);
        jackson3Vo.setSex("男");

        String jsonString = JSON.toJSONString(jackson3Vo);
        return ResponseEntity.ok(jsonString);
    }
}
