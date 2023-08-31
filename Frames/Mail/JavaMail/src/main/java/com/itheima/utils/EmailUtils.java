package com.itheima.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.search.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmailUtils {
    static Logger logger = LoggerFactory.getLogger("INFO");

    // 执行日期标识
    static String dateTimeFlag;
    // 附件名称关键词
    private static String FILE_NAME_FLAG1 = "加黑***明细";
    private static String FILE_NAME_FLAG2 = "加黑";

    // Runtime.getRuntime().availableProcessors()
    public static ExecutorService threadPool = Executors.newFixedThreadPool(1);

    // 邮箱附件保存路径
    private static String FILE_SAVE_PATH = "/Users/deng4j/Downloads/";

    // 读取的发件人
    private static String fromEmail = "发件人";

    /**
     * 创建搜索条件，并获取邮件...第二天凌晨10分之后执行，查询前一天零点到今天零点的数据
     *
     * @param folder
     * @throws Exception
     */
    private void searchMails(Folder folder) throws Exception {
        //建立搜索条件继承自SearchTerm，像根据发件人，主题搜索，邮件标记FlagTerm等，
        // FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false); //false代表未读，true代表已读
        // 搜索昨天收到的的所有邮件
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date end = calendar.getTime();

        SearchTerm comparisonTermGe = new SentDateTerm(ComparisonTerm.GE, start);
        SearchTerm comparisonTermLe = new SentDateTerm(ComparisonTerm.LE, end);
        FromStringTerm fromStringTerm = new FromStringTerm(fromEmail);
        SearchTerm andTerm = new AndTerm(new SearchTerm[]{comparisonTermGe, comparisonTermLe, fromStringTerm});
        logger.info("SearchTerm start: {}, end: {}, fromEmail: {}", DateUtil.format(start, "yyyy-MM-dd HH:mm:ss"), DateUtil.format(end, "yyyy-MM-dd HH:mm:ss"), fromEmail);
        //
        Message[] messages = folder.search(andTerm); //根据设置好的条件获取message
        logger.info("search邮件: " + messages.length + "封, SearchTerm:" + andTerm.getClass());
        // FetchProfile fProfile = new FetchProfile(); // 选择邮件的下载模式,
        // fProfile.add(FetchProfile.Item.ENVELOPE); // 根据网速选择不同的模式
        // folder.fetch(messages, fProfile);// 选择性的下载邮件
        // 5. 循环处理每个邮件并实现邮件转为新闻的功能
        for (int i = 0; i < messages.length; i++) {
            // 单个邮件
            logger.info("---第" + i + "邮件开始------------");
            mailReceiver(messages[i]);
            logger.info("---第" + i + "邮件结束------------");
            // 邮件读取备份保存，用来校验
            //messages[i].writeTo(new FileOutputStream(FILE_SAVE_PATH + "pop3Mail_" + messages[i].getMessageNumber() + ".eml"));
        }
    }

    /**
     * 解析邮件
     *
     * @param msg 邮件对象
     * @throws Exception
     */
    public static void mailReceiver(Message msg) {
        try {
            // 发件人信息
            Address[] froms = msg.getFrom();
            String mailSubject = transferChinese(msg.getSubject());
            if (froms != null) {
                InternetAddress addr = (InternetAddress) froms[0];
                logger.info("发件人地址:" + addr.getAddress() + ", 发件人显示名:" + transferChinese(addr.getPersonal()));
            } else {
                logger.error("msg.getFrom() is null... subject:" + mailSubject);
            }
            Date sentDate = msg.getSentDate();
            logger.info("邮件主题: {}, sentDate: {}", mailSubject, sentDate == null ? null : DateUtil.format(sentDate, "yyyy-MM-dd HH:mm:ss"));

            // getContent() 是获取包裹内容, Part相当于外包装
            Object content = msg.getContent();
            if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                reMultipart(multipart);
            } else if (content instanceof Part) {
                Part part = (Part) content;
                rePart(part);
            } else {
                String contentType = msg.getContentType();
                if (contentType != null && contentType.startsWith("text/html")) {
                    logger.warn("---类型:" + contentType);
                } else {
                    logger.warn("---类型:" + contentType);
                    logger.warn("---内容:" + msg.getContent());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 把邮件主题转换为中文.
     */
    public static String transferChinese(String strText) throws UnsupportedEncodingException {

        if (StrUtil.isBlank(strText)) {
            return null;
        }
        strText = MimeUtility.encodeText(new String(strText.getBytes(), "UTF-8"), "UTF-8", "B");
        strText = MimeUtility.decodeText(strText);
        return strText;
    }

    /**
     * 单个处理下载好的文件
     *
     * @param tempFilePath
     */
    private static void dealBlacklist(String tempFilePath) {
        if (StrUtil.isBlank(tempFilePath)) {
            return;
        }
        logger.info("to deal with blacklist Excel file: {}", tempFilePath);
    }

    /**
     * @param part 解析内容
     * @throws Exception
     */
    private static void rePart(Part part) {
        String tempFilePath = null;
        try {
            // 附件
            if (part.getDisposition() != null) {
                // 邮件附件
                String strFileName = MimeUtility.decodeText(part.getFileName()); //MimeUtility.decodeText解决附件名乱码问题
                logger.info("发现附件: {}, 内容类型: {} ", strFileName, MimeUtility.decodeText(part.getContentType()));
                // 读取附件字节并存储到文件中. xls/xlsx
                String fileType = strFileName.substring(strFileName.lastIndexOf(".") + 1);
                if ((fileType.equals("xlsx") || fileType.equals("xls")) && (strFileName.contains(FILE_NAME_FLAG1) || strFileName.contains(FILE_NAME_FLAG2))) {
                    InputStream in = part.getInputStream();// 打开附件的输入流
                    tempFilePath = FILE_SAVE_PATH + dateTimeFlag + strFileName;
                    FileOutputStream out = new FileOutputStream(tempFilePath);
                    int data;
                    while ((data = in.read()) != -1) {
                        out.write(data);
                    }
                    in.close();
                    out.close();
                } else {
                    logger.info("not what we need file, discard it: {}", strFileName);
                }
            } else {
                // 邮件内容
                if (part.getContentType().startsWith("text/plain") || part.getContentType().startsWith("Text/Plain")) {
                    logger.info("Content文本内容：" + part.getContent());
                } else if (part.getContentType().startsWith("text/html")) {
//                    logger.info("HTML内容：" + part.getContent());
                    logger.debug("HTML内容，，不记录日志展示。。");
                } else {
                    logger.debug("!其它ContentType:" + part.getContentType() + " ?内容：" + part.getContent());
                }
            }
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 单个处理黑名单文件，放入线程池处理
            String finalTempFilePath = tempFilePath;
            threadPool.execute(() -> {
                dealBlacklist(finalTempFilePath);
            });
        }
    }


    /**
     * @param multipart // 接卸包裹（含所有邮件内容(包裹+正文+附件)）
     * @throws Exception
     */
    private static void reMultipart(Multipart multipart) throws Exception {
        logger.debug("Multipart邮件共有" + multipart.getCount() + "部分组成");
        // 依次处理各个部分
        for (int j = 0, n = multipart.getCount(); j < n; j++) {
            Part part = multipart.getBodyPart(j);
            // 解包, 取出 MultiPart的各个部分, 每部分可能是邮件内容, 也可能是另一个小包裹(MultipPart)
            if (part.getContent() instanceof Multipart) {
                logger.debug("部分" + j + "的ContentType: " + part.getContentType() + ", to reMultipart() ");
                Multipart p = (Multipart) part.getContent();// 转成小包裹
                //递归迭代
                reMultipart(p);
            } else {
                logger.debug("部分" + j + "的ContentType: " + part.getContentType() + ", to rePart() ");
                rePart(part);
            }
        }
    }
}
