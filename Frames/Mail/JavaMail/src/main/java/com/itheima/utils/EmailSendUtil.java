package com.itheima.utils;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * 发送邮件时设置
 */
public class EmailSendUtil {


    /**
     * 设置删除标记，一定要记得调用saveChanges()方法
     *
     * Flags.Flag.DELETED：删除
     * Flags.Flag.SEEN：已读
     */
    public static void setDeleteFlag(Message message) throws MessagingException {
        message.setFlag(Flags.Flag.DELETED, true);
        message.saveChanges();
    }

    /**
     * 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)
     */
    public static void setHeader(Message message) throws MessagingException, UnsupportedEncodingException {
        message.setHeader("Disposition-Notification-To", String.valueOf(message.getFrom()));
        message.reply(true);
        message.saveChanges();
    }
}
