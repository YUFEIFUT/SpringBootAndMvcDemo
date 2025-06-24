package com.fei.shutdownhookdemo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * 方式一：实现DisposableBean接口，实现destroy方法
 * <p>
 * 适用场景：
 *   - 需要在Bean销毁时做一些资源释放、日志、清理等操作
 *   - 适合于单例Bean
 * <p>
 * 特点：
 *   - destroy方法会在Spring容器关闭时自动调用
 *   - 只能有一个destroy方法（接口约定）
 *   - 侵入性较强（需要实现接口）
 * <p>
 * 与@PreDestroy、事件监听的区别：
 *   - @PreDestroy更灵活，可以和其他注解共用
 *   - 事件监听可以监听整个容器的关闭，而不是单个Bean
 */
@Component
public class DisposableBeanDemo implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("【DisposableBean】destroy方法被调用，执行资源释放逻辑...");
    }
} 