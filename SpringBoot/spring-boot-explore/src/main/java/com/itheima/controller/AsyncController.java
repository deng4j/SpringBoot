package com.itheima.controller;

import com.itheima.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@RequestMapping("asyncs")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping
    public String testTask() throws Exception {
        long start=System.currentTimeMillis();
        Future<String> doTask1 = asyncService.doTask1();
        Future<String> doTask2 = asyncService.doTask2();
        Future<String> doTask3 = asyncService.doTask3();

        while (true){
            if (doTask1.isDone()&&doTask2.isDone()&&doTask3.isDone()){
                break;
            }
            Thread.sleep(1000);
        }

        long end =System.currentTimeMillis();

        return  "总耗时："+(end-start);
    }
}
