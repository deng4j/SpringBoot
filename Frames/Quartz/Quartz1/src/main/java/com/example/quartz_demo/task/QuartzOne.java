package com.example.quartz_demo.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class QuartzOne implements Job {

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        System.out.println("触发港1："+new Date());
    }
}
