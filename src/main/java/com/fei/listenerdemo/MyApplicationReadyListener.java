package com.fei.listenerdemo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationReadyListener {

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
        // 这里写你的初始化逻辑，比如加载缓存等
        System.out.println("\n【ApplicationReadyEvent】应用已启动，可以在这里做缓存预热等操作，这也是缓存预热的一个时机。\n");
    }
}