package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("thy")
public class ThymeleafController {
    @GetMapping
    public String show(Model model){
        model.addAttribute("name","李四");
        return "thymeleaf";
    }
}
