package com.example.quartz_demo.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;
import java.util.Set;

public class HelloTask implements Job {

    public HelloTask() {
        System.out.println("\033[32m" + "创建HelloTask实例" + "\033[0m");
    }


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //通过上下文对象JobExecutionContext可以获取到任务相关参数
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Set<Map.Entry<String, Object>> entries = jobDataMap.entrySet();
        entries.forEach((entry) -> {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("\033[32m" + "key:"+key+" value:"+value + "\033[0m");
        });
    }


    public static void main(String[] args) {
        try {
            //1:创建调度器
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //2:创建任务实例
            JobDetail jobDetail = JobBuilder.newJob(HelloTask.class).
                withIdentity("JobDetail_1").
                build();

            //设置参数，在定时任务执行时可以动态获取这些参数
            jobDetail.getJobDataMap().put("key1","value1");
            jobDetail.getJobDataMap().put("key2","value2");

            //3:创建触发器Trigger
            Trigger trigger = TriggerBuilder.newTrigger().
                withIdentity("Trigger_1").
                withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).
                build();

            //4:触发器和任务关联
            scheduler.scheduleJob(jobDetail,trigger);

            //5:启动任务调度器
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
