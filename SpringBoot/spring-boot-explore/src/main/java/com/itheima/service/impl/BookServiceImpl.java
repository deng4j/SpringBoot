package com.itheima.service.impl;

import com.itheima.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public void show() {
        System.out.println("bookservice");
    }
}
