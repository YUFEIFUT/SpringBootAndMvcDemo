package com.fei.shutdownhookdemo;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * 方式三：实现ApplicationListener接口，监听Spring容器关闭事件（ContextClosedEvent）
 * <p>
 * 适用场景：
 *   - 需要在整个Spring容器关闭时做一些全局性的资源释放、日志、通知等操作
 *   - 适合于需要感知容器关闭的场景
 * <p>
 * 特点：
 *   - 可以监听整个容器的关闭事件，做全局处理
 *   - 可以有多个监听器，互不影响
 *   - 非侵入性，和Bean生命周期解耦
 * <p>
 * 与DisposableBean、@PreDestroy的区别：
 *   - DisposableBean/@PreDestroy只针对单个Bean
 *   - 事件监听可以做全局处理，适合跨Bean、跨模块的资源释放
 */
@Component
public class ContextClosedEventListenerDemo implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("【ContextClosedEvent】监听到Spring容器关闭，执行全局资源释放逻辑...");
    }
} 