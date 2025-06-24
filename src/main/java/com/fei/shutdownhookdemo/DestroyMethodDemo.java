package com.fei.shutdownhookdemo;

/**
 * 方式四：自定义destroy-method
 * <p>
 * 适用场景：
 *   - 需要在Bean销毁时做资源释放、日志、清理等操作
 *   - 适合于第三方Bean或无法加注解/实现接口的Bean
 * <p>
 * 特点：
 *   - 通过@Bean(destroyMethod="xxx")或XML配置destroy-method指定
 *   - 非侵入性，不需要修改Bean源码
 *   - 只对Spring管理的Bean有效
 * <p>
 * 与DisposableBean、@PreDestroy、事件监听的区别：
 *   - destroy-method适合第三方Bean
 *   - DisposableBean/@PreDestroy适合自定义Bean
 *   - 事件监听适合全局处理
 */
public class DestroyMethodDemo {
    public void customDestroy() {
        System.out.println("【destroy-method】customDestroy方法被调用，执行资源释放逻辑...");
    }
} 