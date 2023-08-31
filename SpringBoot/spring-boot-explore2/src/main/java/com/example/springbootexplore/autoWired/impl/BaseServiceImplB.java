package com.example.springbootexplore.autoWired.impl;

import com.example.springbootexplore.autoWired.IBaseService;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImplB implements IBaseService {

    @Override
    public void getMsa() {
        System.out.println("-----------BaseServiceImplB----------");
    }
}
