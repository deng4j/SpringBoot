package com.example.auto_email.utils;

import com.sun.mail.imap.IMAPFolder;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 解析邮箱服务器返回的邮件
 */
public class MailParsingTool {

    /***
     * 获取邮箱的基本邮件信息
     * @param folder   收件箱对象
     * @return 返回邮箱基本信息
     */
    public static Map<String, Integer> emailInfo(Folder folder) throws MessagingException {
        Map<String, Integer> emailInfo = new HashMap<>();
        // 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数
        emailInfo.put("unreadMessageCount", folder.getUnreadMessageCount());        //未读邮件数
        // 由于POP3协议无法获知邮件的状态,所以下面(删除、新邮件)得到的结果始终都是为0
        emailInfo.put("deletedMessageCount", folder.getDeletedMessageCount());      //删除邮件数
        emailInfo.put("newMessageCount", folder.getNewMessageCount());              //新邮件
        // 获得收件箱中的邮件总数
        emailInfo.put("messageCount", folder.getMessageCount());                    //邮件总数
        return emailInfo;
    }

    /***
     * 获得邮件主题
     * @param msg 邮件内容
     * @return 解码后的邮件主题
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
        return decodeText(msg.getSubject());
    }

    /***
     * 获得邮件发件人
     * @param msg 邮件内容
     * @return 姓名 <Email地址>
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1) {
            throw new MessagingException("没有发件人!");
        }

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";
        return from;
    }

    /***
     * 获得邮件发件人地址
     */
    public static String getAddress(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1) {
            throw new MessagingException("没有发件人!");
        }

        InternetAddress address = (InternetAddress) froms[0];
        from = address.getAddress();
        return from;
    }

    /***
     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
     * type可选值
     * <p>Message.RecipientType.TO  收件人</p>
     * <p>Message.RecipientType.CC  抄送</p>
     * <p>Message.RecipientType.BCC 密送</p>
     * @param msg  邮件内容
     * @param type 收件人类型
     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
     * @throws MessagingException
     */
    public static String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
        StringBuilder recipientAddress = new StringBuilder();
        Address[] addresss = null;
        if (type == null) {
            addresss = msg.getAllRecipients();
        } else {
            addresss = msg.getRecipients(type);
        }

        if (addresss == null || addresss.length < 1) {
            if (type == null) {
                throw new MessagingException("没有收件人!");
            } else if ("Cc".equals(type.toString())) {
                throw new MessagingException("没有抄送人!");
            } else if ("Bcc".equals(type.toString())) {
                throw new MessagingException("没有密送人!");
            }
        }

        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress) address;
            recipientAddress.append(internetAddress.toUnicodeString()).append(",");
        }
        //删除最后一个逗号
        recipientAddress.deleteCharAt(recipientAddress.length() - 1);
        return recipientAddress.toString();
    }

    /***
     * 获得邮件发送时间
     * @param msg 邮件内容
     * @return 默认返回：yyyy年mm月dd日 星期X HH:mm
     * @throws MessagingException
     */
    public static String getSentDate(MimeMessage msg, String pattern) throws MessagingException {
        Date receivedDate = msg.getSentDate();
        if (receivedDate == null) return "";
        if (pattern == null || "".equals(pattern)) pattern = "yyyy年MM月dd日 E HH:mm ";
        return new SimpleDateFormat(pattern).format(receivedDate);
    }

    /***
     * 判断邮件是否已读  www.2cto.com
     * @param msg 邮件内容
     * @return 如果邮件已读返回true, 否则返回false
     * @throws MessagingException
     */
    public static boolean isSeen(MimeMessage msg) throws MessagingException {
        return msg.getFlags().contains(Flags.Flag.SEEN);
    }

    /***
     * FLAGGED：表示邮件是否为回收站中的邮件。
     */
    public static boolean isFLAGGED(MimeMessage msg) throws MessagingException {
        return msg.getFlags().contains(Flags.Flag.FLAGGED);
    }

    /***
     * 判断邮件是否需要阅读回执
     * @param msg 邮件内容
     * @return 需要回执返回true, 否则返回false
     * @throws MessagingException
     */
    public static boolean isReplySign(MimeMessage msg) throws MessagingException {
        boolean replySign = false;
        String[] headers = msg.getHeader("Disposition-Notification-To");
        if (headers != null) replySign = true;
        return replySign;
    }

    /***
     * 获得邮件的优先级
     * @param msg 邮件内容
     * @return 1(High):紧急  3:普通(Normal)  5:低(Low)
     * @throws MessagingException
     */
    public static String getPriority(MimeMessage msg) throws MessagingException {
        String priority = "普通";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String headerPriority = headers[0];
            if (headerPriority.contains("1") || headerPriority.contains("High")) priority = "紧急";
            else if (headerPriority.contains("5") || headerPriority.contains("Low")) priority = "低";
            else priority = "普通";
        }
        return priority;
    }

    /***
     * 获得邮件文本内容
     * @param part    邮件体
     * @param content 存储邮件文本内容的字符串
     * @throws MessagingException
     * @throws IOException
     */
    public static void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, content);
            }
        }
    }

    /***
     * 文本解码
     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
     * @return 解码后的文本
     * @throws UnsupportedEncodingException
     */
    public static String decodeText(String encodeText) throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }

    /***
     * 判断邮件中是否包含附件 （Part为Message接口）
     * @param part 邮件内容
     * @return 邮件中存在附件返回true，不存在返回false
     * @throws MessagingException
     * @throws IOException
     */
    public static boolean isContainAttachment(Part part) throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.contains("application")) {
                        flag = true;
                    }
                    if (contentType.contains("name")) {
                        flag = true;
                    }
                }
                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part) part.getContent());
        }
        return flag;
    }

    /***
     * 保存附件
     * @param part    邮件中多个组合体中的其中一个组合体
     * @param destDir 附件保存目录
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void saveAttachment(Part part, String destDir) throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();    //复杂体邮件
            //复杂体邮件包含多个邮件体
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                //获得复杂体邮件中其中一个邮件体
                BodyPart bodyPart = multipart.getBodyPart(i);
                //某一个邮件体也有可能是由多个邮件体组成的复杂体
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    InputStream is = bodyPart.getInputStream();
                    saveFile(is, destDir, decodeText(bodyPart.getFileName()));
                } else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachment(bodyPart, destDir);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.contains("name") || contentType.contains("application")) {
                        saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName()));
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent(), destDir);
        }
    }

    /***
     * 读取输入流中的数据保存至指定目录 
     * @param is       输入流
     * @param fileName 文件名
     * @param destDir  文件存储目录
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void saveFile(InputStream is, String destDir, String fileName) throws FileNotFoundException, IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destDir + fileName));
        int len = -1;
        while ((len = bis.read()) != -1) {
            bos.write(len);
            bos.flush();
        }
        bos.close();
        bis.close();
    }


    public static Message[] getNotReadMsgs(Folder folder) throws MessagingException {
        //获取未读邮件
        Message[] messages = folder.getMessages(folder.getMessageCount()-folder.getUnreadMessageCount()+1,folder.getMessageCount());
        return messages;
    }

    public static void moveMessage(IMAPFolder sourceFolder, IMAPFolder targetFolder, MimeMessage[] messages) throws MessagingException {
        if (messages == null) {
            return;
        }
        try {
            sourceFolder.moveMessages(messages, targetFolder);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 测试工具
     */
    public static void parseMail(Message message) throws MessagingException, IOException {
        MimeMessage msg = (MimeMessage) message;
        System.out.println("-----------解析第" + msg.getMessageNumber() + "封邮件---------------");
        System.out.println("主题: " + MailParsingTool.getSubject(msg));
        System.out.println("发件人: " + MailParsingTool.getFrom(msg));
        System.out.println("收件人：" + MailParsingTool.getReceiveAddress(msg, Message.RecipientType.TO));
        System.out.println("发送时间：" + MailParsingTool.getSentDate(msg, null));
        System.out.println("是否已读：" + MailParsingTool.isSeen(msg));
        System.out.println("邮件优先级：" + MailParsingTool.getPriority(msg));
        boolean idReplySign = MailParsingTool.isReplySign(msg);
        System.out.println("是否需要回执：" + idReplySign);
        System.out.println("邮件大小：" + msg.getSize() * 1024 + "kb");
        boolean isContainerAttachment = MailParsingTool.isContainAttachment(msg);
        System.out.println("是否包含附件：" + isContainerAttachment);
        if (isContainerAttachment) {
            //获取文件的存储目录
            String path = MailParsingTool.class.getClassLoader().getResource("").getPath();
            //获取文件的前缀
            String strFile = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            MailParsingTool.saveAttachment(msg, path + strFile + "_"); //保存附件
        }
        //用来存储正文的对象
        StringBuffer content = new StringBuffer();
        //处理邮件正文
        MailParsingTool.getMailTextContent(msg, content);
        System.out.println("邮件正文：" + content);
        System.out.println("-----------第" + msg.getMessageNumber() + "封邮件解析结束------------");
    }
}