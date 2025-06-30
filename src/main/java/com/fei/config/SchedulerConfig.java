package com.fei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    /*
    这里不能用构造注入，会循环依赖：
┌─────┐
|  schedulerConfig defined in file [E:\MyFile\java\SpringBoot\SpringBoot源码\springbootsourcecode\target\classes\com\fei\config\SchedulerConfig.class]
└─────┘

    这是因为 ThreadPoolTaskScheduler 注入容器的时机至少得在当前类构造之后，所以就出现了单个类的循环依赖【哈哈哈哈，我之前还以为循环依赖只有两个及以上的bean相关依赖成环的情况下才会出现呢，没想到还是年轻了啊】
     */
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    // 定义线程池给@Scheduled 的 demo 用的
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // 设置线程池大小
        scheduler.setThreadNamePrefix("scheduled-task-"); // 设置线程名前缀
        scheduler.setWaitForTasksToCompleteOnShutdown(true); // 优雅停机
        scheduler.setAwaitTerminationSeconds(60); // 等待终止时间
        return scheduler;
    }

    // 启用定时任务并指定线程池
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}