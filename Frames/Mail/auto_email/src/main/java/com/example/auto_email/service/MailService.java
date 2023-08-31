package com.example.auto_email.service;

import com.example.auto_email.domain.Msg;
import com.example.auto_email.config.MailProperties;

public interface MailService {
    void sendSimpleMail(MailProperties.Properties mType, Msg msg);

    void receiveMailSchedule(MailProperties.Properties mType);
}
