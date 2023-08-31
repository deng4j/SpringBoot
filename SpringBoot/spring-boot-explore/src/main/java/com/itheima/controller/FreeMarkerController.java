package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ftl")
public class FreeMarkerController {

    @GetMapping
    public String show(Model model){
        model.addAttribute("name","张三");
        return "freemarker";
    }
}
