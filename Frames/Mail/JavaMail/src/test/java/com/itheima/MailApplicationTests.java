package com.itheima;

import com.itheima.service.EmailService;
import com.itheima.utils.MailParsingTool;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.protocol.IMAPProtocol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static com.itheima.utils.MailParsingTool.parseMail;
import static javax.mail.Message.RecipientType.*;

/**
 * JavaMail中主要类有:
 * Session： 表示会话，即客户端与邮件服务器之间的会话！想获得会话需要给出账户和密码，当然还要给出服务器名称。在邮件服务中的Session对象，就相当于连接数据库时的Connection对象。
 * MimeMessage： 表示邮件类，它是Message的子类。它包含邮件的主题（标题）、内容，收件人地址、发件人地址，还可以设置抄送和暗送，甚至还可以设置附件。
 * Transport： 用来发送邮件。它是发送器！
 */
@SpringBootTest
class MailApplicationTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testQQsim(){
        emailService.sendSimpleMail("deng4j@aliyun.com","dasd","bivu312312");
    }

    @Test
    void SimpleSendEmail() throws MessagingException {
        /*
         * 1 获取Session
         */
        /*设置服务器参数*/
        Properties props = new Properties();
        //设置服务器主机名
        props.put("mail.smtp.host", "smtp.163.com");
        //设置需要认证
        props.put("mail.smtp.auth", "true");
        // 启用TLS加密
        props.put("mail.smtp.starttls.enable", "true");
        /*
         * 根据服务器参数集合和认证器来获取Session实例
         *
         * Authenticator是一个接口表示认证器，即校验客户端的身份。
         * 我们需要自己来实现这个接口，实现这个接口需要使用账户和密码。
         */
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //使用自己的账户和密码，有些邮件服务器可能需要的是申请的授权码
                return new PasswordAuthentication("xxxx@163.com", "xxxx");
            }
        });
        // 设置debug模式，将会输出日志信息
        session.setDebug(true);

        /*
         * 2 根据Session创建MimeMessage对象
         * MimeMessage中包含了各种可以设置的邮件属性
         */
        MimeMessage msg = new MimeMessage(session);
        //设置发信人
        msg.setFrom(new InternetAddress("xxx@163.com"));
        //设置收信人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(TO, "xxx@qq.com");
        //设置抄送人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(CC, "xxx@yeah.net");
        //设置暗送人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(BCC, "xxx@xx.com");
        //设置邮件主题（标题）
        msg.setSubject("第一封邮件");
        //设置邮件内容（正文）和类型
        msg.setContent("说点啥呢？", "text/plain;charset=utf-8");
    }

    /**
     * 使用pop3协议收取邮件
     */
    @Test
    void ReceiveEmailWithPOP3() throws MessagingException, IOException {
        //POP3主机名
        String host = "pop3.163.com";
        //设置传输协议
        String protocol = "pop3";
        //用户账号
        String username = "deng4j@163.com";
        //密码或者授权码
        String password = "YDYJTCJGRKQTVYNO";


        /*
         * 获取Session
         */
        Properties props = new Properties();
        //协议
        props.setProperty("mail.store.protocol", protocol);
        //POP3主机名
        props.setProperty("mail.pop3.host", host);
        props.setProperty("mail.smtp.auth", "true");
        // SSL安全连接参数
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.socketFactory.port", String.valueOf(995));
        // 解决DecodingException: BASE64Decoder: but only got 0 before padding character (=)
        props.setProperty("mail.mime.base64.ignoreerrors", "true");

        Session session = Session.getInstance(props);
        session.setDebug(true);

        /*
         * 获取Store，一个Store对象表示整个邮箱的存储
         */
        URLName urlName = new URLName(protocol, host, 110, null, username, password);
        Store store = session.getStore(urlName);
        //连接邮件服务器
        store.connect();
        //要收取邮件，我们需要通过Store访问指定的Folder（文件夹），通常是INBOX表示收件箱
        //获取邮箱的邮件夹，通过pop3协议获取某个邮件夹的名称只能为inbox，不区分大小写
        Folder folder = store.getFolder("INBOX");
        //打开邮箱方式（邮件访问权限），这里的只读权限
        folder.open(Folder.READ_ONLY);

        //打印邮件总数/新邮件数量/未读数量/已删除数量:
        System.out.println("\033[32m" + "------------------------" + "\033[0m");
        System.out.println("Total messages: " + folder.getMessageCount());
        System.out.println("New messages: " + folder.getNewMessageCount());
        System.out.println("Unread messages: " + folder.getUnreadMessageCount());
        System.out.println("Deleted messages: " + folder.getDeletedMessageCount());

        // 获得邮件夹Folder内的所有邮件Message对象，一个Message代表一个邮件
        Message[] messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            System.out.println("\033[32m" + "------------解析邮件------------" + "\033[0m");
            parseMail(messages[i]);
            System.out.println("\033[32m" + "------------end------------" + "\033[0m");
        }
        //传入true表示删除操作会同步到服务器上（即删除服务器收件箱的邮件），无参方法默认传递true
        folder.close();
        store.close();
    }

    /**
     * 使用imap协议收取邮件
     */
    @Test
    void ReceiveEmailWithIMAP() throws MessagingException, IOException {
        //imap主机名
        String host = "imap.163.com";
        //设置传输协议
        String protocol = "imap";
        //用户账号
        String username = "deng4j@163.com";
        //密码或者授权码
        String password = "YDYJTCJGRKQTVYNO";


        /*
         * 获取Session
         */
        Properties props = new Properties();
        //协议
        props.setProperty("mail.store.protocol", protocol);
        //imap主机名
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.smtp.auth", "true");
        // SSL安全连接参数
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.socketFactory.port", String.valueOf(993));
        // 解决DecodingException: BASE64Decoder: but only got 0 before padding character (=)
        props.setProperty("mail.mime.base64.ignoreerrors", "true");

        Session session = Session.getInstance(props);
        session.setDebug(true);

        //这部分就是解决异常的关键所在，设置IAMP ID信息
        HashMap IAM = new HashMap();
        //带上IMAP ID信息，由key和value组成，例如name，version，vendor，support-email等。
        // 这个value的值随便写就行
        IAM.put("name", "myname");
        IAM.put("version", "1.0.0");
        IAM.put("vendor", "myclient");
        IAM.put("support-email", "testmail@test.com");

        /*
         * 获取Store，一个Store对象表示整个邮箱的存储
         */
        URLName urlName = new URLName(protocol, host, 143, null, username, password);
        IMAPStore store = (IMAPStore) session.getStore(urlName);
        //连接邮件服务器
        store.connect();
        store.id(IAM);
        //要收取邮件，我们需要通过Store访问指定的Folder（文件夹），通常是INBOX表示收件箱
        //收件箱的邮件夹
        IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
        //解决异常
        folder.doOptionalCommand("ID not supported",
                new IMAPFolder.ProtocolCommand() {
                    @Override
                    public Object doCommand(IMAPProtocol p)
                            throws ProtocolException {
                        return p.id(IAM);
                    }
                });

        //打开邮箱方式
        folder.open(Folder.READ_WRITE);

        //打印邮件总数/新邮件数量/未读数量/已删除数量:
        System.out.println("\033[32m" + "------------------------" + "\033[0m");
        System.out.println("Total messages: " + folder.getMessageCount());
        System.out.println("New messages: " + folder.getNewMessageCount());
        System.out.println("Unread messages: " + folder.getUnreadMessageCount());
        System.out.println("Deleted messages: " + folder.getDeletedMessageCount());

        // 获得邮件夹Folder内的所有邮件Message对象，一个Message代表一个邮件
        Message[] messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            System.out.println("\033[32m" + "------------解析邮件------------" + "\033[0m");
            MimeMessage msg = (MimeMessage) messages[i];
            System.out.println("-----------解析第" + msg.getMessageNumber() + "封邮件---------------");
            String subject = MailParsingTool.getSubject(msg);
            System.out.println("主题: " + subject);
            System.out.println("发件人: " + MailParsingTool.getFrom(msg));
            System.out.println("收件人：" + MailParsingTool.getReceiveAddress(msg, Message.RecipientType.TO));
            System.out.println("发送时间：" + MailParsingTool.getSentDate(msg, null));
            boolean seen = MailParsingTool.isSeen(msg);
            System.out.println("是否已读：" + seen);
            if (!seen) {
                //imap的massage也是不可以修改的，这是协议的一个限制，调用saveChanges必定会抛该异常。
//                folder.setFlags(new Message[]{msg}, new Flags(Flags.Flag.SEEN), true); //方式一
                messages[i].setFlag(Flags.Flag.SEEN, true); //方式二
            }
            System.out.println("\033[32m" + "------------end------------" + "\033[0m");
        }
        //传入true表示删除操作会同步到服务器上（即删除服务器收件箱的邮件），无参方法默认传递true
        folder.close();
        store.close();
    }

    /**
     * 发送附件
     * 如果想发送带有附件邮件，那么需要设置邮件的内容为MimeMultiPart
     * <p>
     * 多部件对象MimeMultiPart，可以理解为是部件的集合。一个Multipart对象可以添加若干个BodyPart，
     * 其中第一个BodyPart是文本，即邮件正文，后面的BodyPart是附件，最后将MimeMultiPart设置到msg的content中。
     */
    @Test
    void SendMultipartEmail() throws MessagingException, IOException {
        /*
         * 1 获取Session
         */
        /*设置服务器参数*/
        Properties props = new Properties();
        //设置服务器主机名
        props.put("mail.smtp.host", "smtp.163.com");
        //设置需要认证
        props.put("mail.smtp.auth", "true");
        // 启用TLS加密
        props.put("mail.smtp.starttls.enable", "true");
        /*
         * 根据服务器参数集合和认证器来获取Session实例
         *
         * Authenticator是一个接口表示认证器，即校验客户端的身份。
         * 我们需要自己来实现这个接口，实现这个接口需要使用账户和密码。
         */
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //使用自己的账户和密码，有些邮件服务器可能需要的是申请的授权码
                return new PasswordAuthentication("ccc@163.com", "ccc");
            }
        });
        // 设置debug模式，将会输出日志信息
        session.setDebug(true);

        /*
         * 2 根据Session创建MimeMessage对象
         *
         * MimeMessage中包含了各种可以设置的邮件属性
         */
        MimeMessage msg = new MimeMessage(session);
        //设置发信人
        msg.setFrom(new InternetAddress("ccc@163.com"));
        //设置收信人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(TO, "ccc@qq.com");
        //设置抄送人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(CC, "cc@yeah.net");
        //设置暗送人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(BCC, "c@ikang.com");
        //设置邮件主题（标题）
        msg.setSubject("附件");
        //设置邮件内容（正文）和类型
        //msg.setContent("<h2><font color=red>2021加油哦!</font></h2>", "text/html;charset =utf-8");

        /*
         * 创建一个多部件对象Multipart，可以理解为是部件的集合。
         * 一个Multipart对象可以添加若干个BodyPart，其中第一个BodyPart是文本，即邮件正文，后面的BodyPart是附件
         */

        Multipart parts = new MimeMultipart();

        /*
         * 设置邮件的内容为多部件内容。
         */
        msg.setContent(parts);

        /*
         * 创建第一个部件，为邮件正文
         */
        BodyPart contentPart = new MimeBodyPart();
        //给部件设置正文内容
        contentPart.setContent("<h2><font color=red>附件来了!</font></h2>", "text/html;charset=utf-8");
        //把部件添加到部件集中
        parts.addBodyPart(contentPart);

        /*
         * 创建第二个附件部件，一张图片
         */

        MimeBodyPart imagepart = new MimeBodyPart();
        //设置附件名称
        imagepart.setFileName("people.jpg");
        //注意，如果在设置文件名称时，文件名称中包含了中文的话，那么需要使用MimeUitlity类来给中文编码：
        //imagepart.setFileName(MimeUtility.encodeText("中文.jpg"));

        //设置附件，二进制文件可以用application/octet-stream，Word文档则是application/msword。

        FileInputStream fileInputStream = new FileInputStream("email\\src\\main\\resources\\people.jpg");
        imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/octet-stream")));

        //把附件添加到部件集中
        parts.addBodyPart(imagepart);

        /*
         * 3 发送邮件
         */
        Transport.send(msg);
    }

    /**
     * 发送内嵌图片HTML
     */
    @Test
    void SendPicEmail() throws MessagingException, IOException {
        /*
         * 1 获取Session
         */

        /*设置服务器参数*/
        Properties props = new Properties();
        //设置服务器主机名
        props.put("mail.smtp.host", "smtp.163.com");
        //设置需要认证
        props.put("mail.smtp.auth", "true");
        // 启用TLS加密
        props.put("mail.smtp.starttls.enable", "true");
        /*
         * 根据服务器参数集合和认证器来获取Session实例
         *
         * Authenticator是一个接口表示认证器，即校验客户端的身份。
         * 我们需要自己来实现这个接口，实现这个接口需要使用账户和密码。
         */
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //使用自己的账户和密码，有些邮件服务器可能需要的是申请的授权码
                return new PasswordAuthentication("xxx@163.com", "xx");
            }
        });
        // 设置debug模式，将会输出日志信息
        session.setDebug(true);

        /*
         * 2 根据Session创建MimeMessage对象
         *
         * MimeMessage中包含了各种可以设置的邮件属性
         */
        MimeMessage msg = new MimeMessage(session);
        //设置发信人
        msg.setFrom(new InternetAddress("cc@163.com"));
        //设置收信人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(TO, "dd@ikang.com");
        //设置抄送人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(CC, "cc@yeah.net");
        //设置暗送人，可以设置多个，采用参数数组或者","分隔
        msg.addRecipients(BCC, "xx@qq.com");
        //设置邮件主题（标题）
        msg.setSubject("这是一封图片HTML邮件");
        //设置邮件内容（正文）和类型
        //msg.setContent("<h2><font color=red>2021加油哦!</font></h2>", "text/html;charset =utf-8");

        /*
         * 创建一个多部件对象Multipart，可以理解为是部件的集合。
         * 一个Multipart对象可以添加若干个BodyPart，其中第一个BodyPart是文本，即邮件正文，后面的BodyPart是附件
         */

        Multipart parts = new MimeMultipart();

        /*
         * 设置邮件的内容为多部件内容。
         */
        msg.setContent(parts);

        /*
         * 创建第一个部件，为邮件正文
         */
        BodyPart contentPart = new MimeBodyPart();
        //给部件设置正文内容
        contentPart.setContent("<h1>Hello图片来了!</h1><p><img src='cid:img1'></p>", "text/html;charset=utf-8");
        //把部件添加到部件集中
        parts.addBodyPart(contentPart);

        /*
         * 创建第二个部件，一张图片，html中的图片也算作附件
         */

        MimeBodyPart imagepart = new MimeBodyPart();
        //设置附件名称
        imagepart.setFileName("people.jpg");
        //注意，如果在设置文件名称时，文件名称中包含了中文的话，那么需要使用MimeUitlity类来给中文编码：
        //imagepart.setFileName(MimeUtility.encodeText("中文.jpg"));

        //设置附件，二进制文件可以用application/octet-stream，Word文档则是application/msword。
        FileInputStream fileInputStream = new FileInputStream("email\\src\\main\\resources\\people.jpg");
        imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "image/jpeg")));

        //设置ContentID与HTML的中的<img src="cid:img01">关联
        imagepart.setContentID("img1");
        //把附件添加到部件集中
        parts.addBodyPart(imagepart);

        /*
         * 3 发送邮件
         */
        Transport.send(msg);
    }
}
