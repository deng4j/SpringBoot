package com.example.auto_email.domain;

import lombok.Data;

@Data
public class Msg {

    private String sendTo;
    private String title;
    private String content;
}
