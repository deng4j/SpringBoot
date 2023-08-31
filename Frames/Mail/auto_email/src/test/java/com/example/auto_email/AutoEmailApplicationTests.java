package com.example.auto_email;

import com.example.auto_email.config.MailProperties;
import com.example.auto_email.domain.Msg;
import com.example.auto_email.service.MailService;
import com.example.auto_email.utils.MailParsingTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutoEmailApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private MailProperties mailProperties;

    @Test
    void test163() {
        mailService.receiveMailSchedule(mailProperties.getM163());
    }

    @Test
    void testqq() {
        mailService.receiveMailSchedule(mailProperties.getMqq());
    }

    @Test
    void testsendqq() {
        Msg toMsg = new Msg();
        toMsg.setTitle("我已收到！");
        toMsg.setContent("感谢您的反馈");
        toMsg.setSendTo("deng4j@aliyun.com");
        mailService.sendSimpleMail(mailProperties.getMqq(),toMsg);
    }

}
