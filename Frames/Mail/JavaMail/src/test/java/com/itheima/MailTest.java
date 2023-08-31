package com.itheima;

import com.sun.mail.util.MailSSLSocketFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@SpringBootTest
public class MailTest {
    public String emailHost = "smtp.163.com";       //发送邮件的主机
    public String transportType = "smtp";           //邮件发送的协议
    public String fromUser = "antladdie";           //发件人名称
    public String fromEmail = "antladdie@163.com";  //发件人邮箱
    public String authCode = "xxxxxxxxxxxxxxxx";    //发件人邮箱授权码
    public String toEmail = "xiaofeng504@qq.com";   //收件人邮箱
    public String subject = "电子专票开具";           //主题信息

    @Test
    public void ClientTestA() throws UnsupportedEncodingException, javax.mail.MessagingException {

        //初始化默认参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", transportType);
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.user", fromUser);
        props.setProperty("mail.from", fromEmail);
        //获取Session对象
        Session session = Session.getInstance(props, null);
        //开启后有调试信息
        session.setDebug(true);

        //通过MimeMessage来创建Message接口的子类
        MimeMessage message = new MimeMessage(session);
        //下面是对邮件的基本设置
        //设置发件人：
        //设置发件人第一种方式：直接显示：antladdie <antladdie@163.com>
        //InternetAddress from = new InternetAddress(sender_username);
        //设置发件人第二种方式：发件人信息拼接显示：蚂蚁小哥 <antladdie@163.com>
        String formName = MimeUtility.encodeWord("蚂蚁小哥") + " <" + fromEmail + ">";
        InternetAddress from = new InternetAddress(formName);
        message.setFrom(from);

        //设置收件人：
        InternetAddress to = new InternetAddress(toEmail);
        message.setRecipient(Message.RecipientType.TO, to);

        //设置抄送人(两个)可有可无抄送人：
        List<InternetAddress> addresses = Arrays.asList(new InternetAddress("1457034247@qq.com"), new InternetAddress("575814158@qq.com"));
        InternetAddress[] addressesArr = (InternetAddress[]) addresses.toArray();
        message.setRecipients(Message.RecipientType.CC, addressesArr);

        //设置密送人 可有可无密送人：
        //InternetAddress toBCC = new InternetAddress(toEmail);
        //message.setRecipient(Message.RecipientType.BCC, toBCC);

        //设置邮件主题
        message.setSubject(subject);

        //设置邮件内容,这里我使用html格式，其实也可以使用纯文本；纯文本"text/plain"
        message.setContent("<h1>蚂蚁小哥祝大家工作顺利！</h1>", "text/html;charset=UTF-8");

        //保存上面设置的邮件内容
        message.saveChanges();

        //获取Transport对象
        Transport transport = session.getTransport();
        //smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
        transport.connect(null, null, authCode);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients()); // 发送
    }

    @Test
    public void ClientTestB() throws IOException, javax.mail.MessagingException {

        // 1：初始化默认参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", transportType);
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.user", fromUser);
        props.setProperty("mail.from", fromEmail);
        // 2：获取Session对象
        Session session = Session.getInstance(props, null);
        session.setDebug(true);

        // 3：创建MimeMessage对象
        MimeMessage message = new MimeMessage(session);

        // 4：设置发件人、收件人、主题、（内容后面设置）
        String formName = MimeUtility.encodeWord("蚂蚁小哥") + " <" + fromEmail + ">";
        InternetAddress from = new InternetAddress(formName);
        message.setFrom(from);
        InternetAddress to = new InternetAddress(toEmail);
        message.setRecipient(Message.RecipientType.TO, to);
        //设置邮件主题
        message.setSubject(subject);
        //邮件发送时间
        message.setSentDate(new Date());

        // 5：设置多资源内容
        //=============== 构建邮件内容：多信息片段关联邮件 使用Content-Type:multipart/related ===============//
        // 5.1：构建一个多资源的邮件块 用来把 文本内容资源 和 图片资源关联；；；related代表多资源关联
        MimeMultipart text_img_related = new MimeMultipart("related");
        //text_img_related.setSubType("related");
        //注：这里为什么填写related的请去查阅Multipart类型或者去文章开头跳转我之前上一篇邮件介绍

        // 5.2：创建图片资源
        MimeBodyPart img_body = new MimeBodyPart();
        DataHandler dhImg = new DataHandler(this.getClass().getResource("static/b.png"));
        img_body.setDataHandler(dhImg); //设置dhImg图片处理
        img_body.setContentID("imgA");  //设置资源图片名称ID

        // 5.3：创建文本资源，文本资源并引用上面的图片ID（因为这两个资源我做了关联）
        MimeBodyPart text_body = new MimeBodyPart();
        text_body.setContent("<img src='cid:imgA' width=100/> 我是蚂蚁小哥！！", "text/html;charset=UTF-8");

        // 5.4：把创建出来的两个资源合并到多资源模块了
        text_img_related.addBodyPart(img_body);
        text_img_related.addBodyPart(text_body);
        //===========================================================================================//

        // 6：设置我们处理好的资源（存放到Message）
        message.setContent(text_img_related);

        // 7：保存上面设置的邮件内容
        message.saveChanges();
        // 8：获取Transport对象
        Transport transport = session.getTransport();
        //9：smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
        transport.connect(null, null, authCode);
        //10：发送邮件
        transport.sendMessage(message, message.getAllRecipients()); // 发送
    }

    @Test
    public void ClientTestC() throws IOException, javax.mail.MessagingException {

        // 1：初始化默认参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", transportType);
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.user", fromUser);
        props.setProperty("mail.from", fromEmail);
        // 2：获取Session对象
        Session session = Session.getInstance(props, null);
        session.setDebug(true);

        // 3：创建MimeMessage对象
        MimeMessage message = new MimeMessage(session);

        // 4：设置发件人、收件人、主题、（内容后面设置）
        String formName = MimeUtility.encodeWord("蚂蚁小哥") + " <" + fromEmail + ">";
        InternetAddress from = new InternetAddress(formName);
        message.setFrom(from);
        InternetAddress to = new InternetAddress(toEmail);
        message.setRecipient(Message.RecipientType.TO, to);
        //设置邮件主题
        message.setSubject(subject);
        //邮件发送时间
        message.setSentDate(new Date());

        //*****邮件内容携带 附件 + （HTML内容+图片）使用Content-Type:multipart/mixed ******//
        // 5：设置一个多资源混合的邮件块 设置此类型时可以同时存在 附件和邮件内容  mixed代表混合
        MimeMultipart mixed = new MimeMultipart("mixed");

        // 5.1：创建一个附件资源
        MimeBodyPart file_body = new MimeBodyPart();
        DataHandler dhFile = new DataHandler(this.getClass().getResource("static/a.zip"));
        file_body.setDataHandler(dhFile); //设置dhFile附件处理
        file_body.setContentID("fileA");  //设置资源附件名称ID
        //file_body.setFileName("拉拉.zip");   //设置中文附件名称（未处理编码）
        file_body.setFileName(MimeUtility.encodeText("一个附件.zip"));   //设置中文附件名称

        // 5.2：先把附件资源混合到 mixed多资源邮件模块里
        mixed.addBodyPart(file_body);

        // 5.3：创建主体内容资源存储对象
        MimeBodyPart content = new MimeBodyPart();
        // 把主体内容混合到资源存储对象里
        mixed.addBodyPart(content);
        // 5.4：设置多资源内容
        //=============== 构建邮件内容：多信息片段邮件 使用Content-Type:multipart/related ===============//
        // 5.4.1：构建一个多资源的邮件块 用来把 文本内容资源 和 图片资源合并；；；related代表多资源关联
        MimeMultipart text_img_related = new MimeMultipart("related");
        //text_img_related.setSubType("related");
        //注：这里为什么填写related的请去查阅Multipart类型或者去文章开头跳转我之前上一篇邮件介绍
        // 5.4.2：把关联的把多资源邮件块 混合到mixed多资源邮件模块里
        content.setContent(text_img_related);

        // 5.4.3：创建图片资源
        MimeBodyPart img_body = new MimeBodyPart();
        DataHandler dhImg = new DataHandler(this.getClass().getResource("static/b.png"));
        img_body.setDataHandler(dhImg); //设置dhImg图片处理
        img_body.setContentID("imgA");  //设置资源图片名称ID

        // 5.4.4：创建文本资源，文本资源并引用上面的图片ID（因为这两个资源我做了关联）
        MimeBodyPart text_body = new MimeBodyPart();
        text_body.setContent("<img src='cid:imgA' width=100/> 我是蚂蚁小哥！！", "text/html;charset=UTF-8");

        // 5.4.5：把创建出来的两个资源合并到多资源模块了
        text_img_related.addBodyPart(img_body);
        text_img_related.addBodyPart(text_body);
        //===========================================================================================//

        // 6：设置我们处理好的资源（存放到Message）
        message.setContent(mixed);

        // 7：保存上面设置的邮件内容
        message.saveChanges();
        // 8：获取Transport对象
        Transport transport = session.getTransport();
        //9：smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
        transport.connect(null, null, authCode);
        //10：发送邮件
        transport.sendMessage(message, message.getAllRecipients()); // 发送
    }

    @Test
    void testQQ() throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {
        Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject("主题");
        StringBuilder builder = new StringBuilder();
        builder.append("胡子&小猿的博客:");
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress("1353976012@qq.com"));

        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com",
                "1353976012@qq.com"
                ,
                "chgqehbkdkorjfea"
        );
        transport.sendMessage(msg, new Address[] { new InternetAddress(
                "deng4j@aliyun.com"
        ) });
        transport.close();
    }

}
