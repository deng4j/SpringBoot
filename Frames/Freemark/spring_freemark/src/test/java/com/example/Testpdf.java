package com.example;

import com.example.utils.PdfTemplateUtil;

import java.net.URISyntaxException;

public class Testpdf {

    public static void main(String[] args) throws URISyntaxException {
        String url = PdfTemplateUtil.class.getClassLoader().getResource("static/images/ys.jpg").toURI().toString();
        System.out.println(url);
    }
}
