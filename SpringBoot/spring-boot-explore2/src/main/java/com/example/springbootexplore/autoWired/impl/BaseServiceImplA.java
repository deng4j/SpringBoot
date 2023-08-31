package com.example.springbootexplore.autoWired.impl;

import com.example.springbootexplore.autoWired.IBaseService;
import org.springframework.stereotype.Service;

@Service("baseServiceImplA")
public class BaseServiceImplA implements IBaseService {

    @Override
    public void getMsa() {
        System.out.println("-----------BaseServiceImplA----------");
    }
}
