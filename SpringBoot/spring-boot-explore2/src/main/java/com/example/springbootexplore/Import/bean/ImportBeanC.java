package com.example.springbootexplore.Import.bean;

import javax.annotation.PostConstruct;

public class ImportBeanC {

    @PostConstruct
    public void postConstruct(){
        System.out.println("\033[32m" + "postConstruct："+this.getClass().getName() + "\033[0m");
    }
}
