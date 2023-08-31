package com.itheima.service.impl;

import com.itheima.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService {

    private static Random random=new Random();

    /**
     *访问其他接口较慢或者做耗时任务时，不想程序卡在那了，可以使用多线程来并行执行
     * 处理任务，异步调用@Async。
     */
    @Async
    @Override
    public Future<String> doTask1() throws Exception {
        System.out.println("task1 start");
        Long start=System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        Long end=System.currentTimeMillis();
        System.out.println("task1 end:"+(end-start)/1000+"秒");
        return new AsyncResult<>("任务1结束");
    }

    @Async
    @Override
    public Future<String> doTask2() throws Exception {
        System.out.println("task2 start");
        Long start=System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        Long end=System.currentTimeMillis();
        System.out.println("task2 end:"+(end-start)/1000+"秒");
        return new AsyncResult<>("任务2结束");
    }

    @Async
    @Override
    public Future<String> doTask3() throws Exception {
        System.out.println("task3 start");
        Long start=System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        Long end=System.currentTimeMillis();
        System.out.println("task3 end:"+(end-start)/1000+"秒");
        return new AsyncResult<>("任务3结束");
    }
}
