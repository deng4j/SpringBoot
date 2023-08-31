package com.itheima.controller;

import com.itheima.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("mail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/{qq}")
    public String sendSimpleEmail(@PathVariable String qq){
        emailService.sendSimpleMail(qq+"@qq.com","你好springboot","我使用了springboot给你发送一封邮箱");
        return "ok";
    }

    @PutMapping("/{qq}")
    public String sendAttachmentMail(@PathVariable String qq, @RequestBody String url){
        File file=new File(url);
        emailService.sendAttachmentMail(qq+"@qq.com","springboot","给你发送了图片",file);
        return "okAtt";
    }

    @PutMapping("/temp/{qq}")
    public String sendTemplateMail(@PathVariable String qq){
        emailService.sendTemplateMail(qq+"@qq.com","springboot","给你发送了图片","mail.html");
        return "sendTemplateMail_Ok";
    }
}
