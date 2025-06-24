package com.fei.shutdownhookdemo;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 方式二：使用@PreDestroy注解
 * <p>
 * 适用场景：
 *   - 需要在Bean销毁时做一些资源释放、日志、清理等操作
 *   - 适合于单例Bean
 * <p>
 * 特点：
 *   - 可以在一个Bean中有多个@PreDestroy方法（但一般只写一个）
 *   - 注解方式，非侵入性
 *   - 只对Spring管理的Bean有效
 * <p>
 * 与DisposableBean、事件监听的区别：
 *   - DisposableBean需要实现接口，侵入性强
 *   - 事件监听可以监听整个容器的关闭，而不是单个Bean
 */
@Component
public class PreDestroyDemo {
    @PreDestroy
    public void cleanUp() {
        System.out.println("【@PreDestroy】cleanUp方法被调用，执行资源释放逻辑...");
    }
} 