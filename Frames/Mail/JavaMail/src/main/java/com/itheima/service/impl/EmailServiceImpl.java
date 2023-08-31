package com.itheima.service.impl;

import com.itheima.email.EmailConfig;
import com.itheima.service.EmailService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void sendSimpleMail(String sendTo, String title, String content) {
        //简单邮件的发送
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(emailConfig.getEmailFrom());
        mailMessage.setTo(sendTo);
        mailMessage.setSubject(title);
        mailMessage.setText(content);

        mailSender.send(mailMessage);
    }


    /**
     *发送带附件的邮箱
     */
    @Override
    public void sendAttachmentMail(String sendTo, String title, String content, File file) {
        MimeMessage msg=mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper=new MimeMessageHelper(msg,true);
            helper.setFrom(emailConfig.getEmailFrom());
            helper.setTo(sendTo);
            helper.setSubject(title);
            helper.setText(content);

            FileSystemResource resource=new FileSystemResource(file);
            helper.addAttachment("附件",resource);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(msg);
    }

    /**
     * 发送模板邮件
     */
    @Override
    public void sendTemplateMail(String sendTo, String title, String content, String mail) {
        MimeMessage msg=mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setFrom(emailConfig.getEmailFrom());
            helper.setTo(sendTo);
            helper.setSubject(title);
            helper.setText(content);

            //封装模板数据
            Map<String,Object> map=new HashMap<>();
            map.put("name","张三");

            //得到模板
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(mail);

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            helper.setText(html,true);


        } catch (MessagingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        mailSender.send(msg);
    }
}
