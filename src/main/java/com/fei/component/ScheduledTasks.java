package com.fei.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    // fixedRate：每隔5秒执行一次，无论上一次任务是否完成，都会按照固定的时间间隔（本例为5秒）启动新的任务。
    // 也就是说，如果任务执行时间超过5秒，可能会出现任务重叠并发执行的情况。
    @Scheduled(fixedRate = 5000)
    public void performTask() {
        System.out.println("Regular task performed at " + System.currentTimeMillis());
    }

    // fixedDelay：在上一个任务执行完成后，等待5秒再启动下一次任务。
    // 也就是说，任务之间的间隔是“上一次任务结束”到“下一次任务开始”之间的时间，任务不会重叠执行。
    @Scheduled(fixedDelay = 5000)
    public void performDelayedTask() {
        System.out.println("Delayed task performed at " + System.currentTimeMillis());
    }

    // 每天晚上12点执行
    @Scheduled(cron = "0 0 0 * * ?")
    public void performTaskUsingCron() {
        System.out.println("Scheduled task using cron expression at " + System.currentTimeMillis());
    }
}