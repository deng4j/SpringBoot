package com.itheima.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class job1 {

    /**
     *springboot的Scheduled,实现任务调度：
     * fixedRate = 1000每秒调一次
     */
    @Scheduled(fixedRate = 1000)
    public void run(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        System.out.println(date);
    }
}
