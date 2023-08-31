package com.example.auto_email.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.example.auto_email.domain.Msg;
import com.example.auto_email.config.MailProperties;
import com.example.auto_email.service.MailService;
import com.example.auto_email.utils.MailParsingTool;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class MailServiceImpl implements MailService {

    private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public void sendSimpleMail(MailProperties.Properties mType, Msg msg) {
        Transport transport = null;
        try {
            //初始化默认参数
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", mType.getSmtp().getProtocol());
            props.setProperty("mail.smtp.host", mType.getSmtp().getHost());
            props.setProperty("mail.user", mType.getUsername());
            props.setProperty("mail.password", mType.getPassword());
            props.put("mail.smtp.auth", "true");

            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);
            //开启后有调试信息
            session.setDebug(false);

            //通过MimeMessage来创建Message接口的子类
            MimeMessage message = new MimeMessage(session);

            // 设置发件人
            InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
            message.setFrom(form);

            message.setSubject(msg.getTitle());
            message.setContent(msg.getContent(), "text/html;charset=UTF-8");

            message.saveChanges();

            transport = session.getTransport();
            transport.connect(mType.getSmtp().getHost(), mType.getUsername(), mType.getPassword());
            transport.sendMessage(message, new Address[]{new InternetAddress(
                    msg.getSendTo()
            )});
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != transport) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void receiveMailSchedule(MailProperties.Properties mType) {
        List<Folder> folderList = new ArrayList<>();
        IMAPStore store = null;
        try {
            //imap主机名
            String host = mType.getImap().getHost();
            //设置传输协议
            String protocol = mType.getImap().getProtocol();
            String port = mType.getImap().getPort();
            //用户账号
            String username = mType.getUsername();
            //密码或者授权码
            String password = mType.getPassword();

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
            session.setDebug(false);

            final Map<String, String> clientParams = new HashMap<String, String>();
            clientParams.put("name", "my-imap");
            clientParams.put("version", "1.0");

            URLName urlName = new URLName(protocol, host, Integer.parseInt(port), null, username, password);
            store = (IMAPStore) session.getStore(urlName);
            store.connect();
            store.id(clientParams);

            List<Message> messageList = new ArrayList<>();

//            Folder defaultFolder = store.getDefaultFolder();
//            Folder[] allFolder = defaultFolder.list();

            //获取邮箱的邮件夹
            IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
            inbox.doOptionalCommand("ID not supported", new IMAPFolder.ProtocolCommand() {
                @Override
                public Object doCommand(IMAPProtocol p) throws ProtocolException {
                    return p.id(clientParams);
                }
            });

            inbox.open(Folder.READ_WRITE);
            Message[] inboxMessages = MailParsingTool.getNotReadMsgs(inbox);
            folderList.add(inbox);
            messageList.addAll(Arrays.asList(inboxMessages));

            IMAPFolder jmailbox = (IMAPFolder) store.getFolder(mType.getImap().getJunkMailbox());
            jmailbox.doOptionalCommand("ID not supported", new IMAPFolder.ProtocolCommand() {
                @Override
                public Object doCommand(IMAPProtocol p) throws ProtocolException {
                    return p.id(clientParams);
                }
            });


            jmailbox.open(Folder.READ_WRITE);
            Message[] jmailboxMessages = jmailbox.getMessages();
            folderList.add(jmailbox);
            messageList.addAll(Arrays.asList(jmailboxMessages));

            for (int i = 0; i < messageList.size(); i++) {
                logger.info("\033[32m" + "------------垃圾邮件------------" + "\033[0m");
                MimeMessage msg = (MimeMessage) messageList.get(i);
                logger.info("\033[32m" + "------------解析第{}封邮件------------" + "\033[0m", i);
                String subject = MailParsingTool.getSubject(msg);
                logger.info("\033[32m" + "------------主题:{}------------" + "\033[0m", subject);
                if (subject.contains("退信")) continue;
                logger.info("\033[32m" + "------------发件人:{}------------" + "\033[0m", MailParsingTool.getFrom(msg));
                boolean seen = MailParsingTool.isSeen(msg);
                if (!seen) {
                    Msg toMsg = new Msg();
                    toMsg.setTitle("我已收到！");
                    toMsg.setContent("感谢您的反馈");
                    toMsg.setSendTo(MailParsingTool.getAddress(msg));
                    sendSimpleMail(mType, toMsg);
                    if (mType.getImap().getJunkMailbox().equals(msg.getFolder().getFullName())) {
                        MailParsingTool.moveMessage(jmailbox, inbox, new MimeMessage[]{msg});
                    }
                    msg.setFlag(Flags.Flag.SEEN, true);
                }
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (CollectionUtil.isNotEmpty(folderList)) {
                try {
                    for (Folder folder : folderList) {
                        folder.close();
                    }
                } catch (MessagingException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }

            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
