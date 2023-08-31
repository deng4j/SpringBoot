package com.itheima.vote.controller;

import com.itheima.vote.domain.Star;
import com.itheima.vote.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("star")
public class StarController {

    @Autowired
    private StarService starService;

    @GetMapping("/{name}")
    public Set<String> getCharts(@PathVariable String name){
        Set<String> starList= starService.getCharts(name);
        return starList;
    }

    @GetMapping
    public  Set<String> getAll(){
        Set<String> starList= starService.getAll();
        return starList;
    }
}
