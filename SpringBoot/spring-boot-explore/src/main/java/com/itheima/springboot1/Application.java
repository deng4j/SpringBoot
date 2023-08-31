package com.itheima.springboot1;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

//扫包方法一
//@ComponentScan({"com.itheima.controller"})

// @SpringBootApplication等价于 @Configuration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication(scanBasePackages = "com.itheima")
//开启异步执行
@EnableAsync
//开启任务调度
@EnableScheduling

public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 使用时：@Async("taskExecutor")
     * @return
     */
    @Bean("taskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数20：线程池创建时候初始化的线程数
        executor.setCorePoolSize(20);
        //最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20);
        //缓冲队列1024：用来缓冲执行任务的队列
        executor.setQueueCapacity(1024);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(0);
        //线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，
        // 当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；
        // 如果执行程序已关闭，则会丢弃该任务
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean
    public CommandLineRunner init(){

        return new CommandLineRunner(){

            @Override
            public void run(String... args) throws Exception {
                System.out.println("CommandLineRunner项目启动初始化");
            }
        };
    }

    @Bean
    public ApplicationRunner  init2(){
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                System.out.println("ApplicationArguments项目启动初始化");
            }
        };
    }

}
