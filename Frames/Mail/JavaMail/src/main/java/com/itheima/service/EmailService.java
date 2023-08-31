package com.itheima.service;

import java.io.File;

public interface EmailService {


    /**
     * 发送简单的邮箱
     */
    void sendSimpleMail(String sendTo,String title,String content);

    /**
     * 发送带附件的邮箱
     */
    void sendAttachmentMail(String sendTo, String title, String content, File file);

    /**
     * 发送模板邮件
     */
    void sendTemplateMail(String sendTo, String title, String content, String mail);


}
