package com.example.quartz_demo.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class QuartzTow implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("触发港2："+new Date());
    }

    // public static void main(String[] args) throws SchedulerException {
    //     QuartzUtil.addJob("taskTow", "trigger2",QuartzTow.class,3);
    // }
}
