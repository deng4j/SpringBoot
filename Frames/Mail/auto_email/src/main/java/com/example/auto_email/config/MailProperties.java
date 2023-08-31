package com.example.auto_email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    @Data
    public static class Properties {
        private String username;
        private String password;
        private Protocol smtp;
        private Protocol imap;
    }

    @Data
    public static class Protocol {
        private String host;
        private String protocol;
        private String port;
        private String JunkMailbox;
    }

    private Properties m163;
    private Properties mqq;
}
