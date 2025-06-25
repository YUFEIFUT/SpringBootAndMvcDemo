package com.fei.listenerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.context.ConfigurableApplicationContext;

/*
1. 目录结构
    新建了 com.fei.listenerdemo 包
    创建了 MyApplicationStartingListener 类，实现了 SpringApplicationRunListener，监听Spring Boot启动的各个阶段
    在 resources/META-INF/spring.factories 注册了该监听器
2. 你启动Spring Boot项目时会看到什么效果？
    控制台会输出如下类似内容（每个阶段都会有）：
        【监听器】Spring Boot 正在启动（starting）...
        【监听器】环境已准备好（environmentPrepared）...
        【监听器】上下文已准备好（contextPrepared）...
        【监听器】上下文已加载（contextLoaded）...
        【监听器】Spring Boot 已启动（started）...
        【监听器】Spring Boot 正在运行（running）...
    如果启动失败，还会输出：
        【监听器】Spring Boot 启动失败（failed）...
3.这个监听器有什么用？
    你可以在Spring Boot生命周期的各个阶段做自定义操作，比如记录日志、统计启动耗时、初始化资源、检查环境变量等。
    例如：在 environmentPrepared 阶段可以读取环境变量，在 started 阶段可以做一些启动后的初始化动作。
4. 实际工程中什么时候用？
    监控应用启动/关闭：比如埋点、上报运维平台
    初始化第三方资源：如连接池、缓存、消息队列等
    动态配置加载：在环境准备好后加载自定义配置
    启动耗时统计：记录每个阶段的耗时，优化启动性能
    异常处理：在 failed 阶段统一处理启动异常

5. 【重要说明】
    MyApplicationStartingListener 不会被加入 Spring IOC 容器。
    - 没有 @Component、@Configuration 等注解，Spring 的包扫描不会自动注册为 Bean。
    - 该类是通过实现 SpringApplicationRunListener 接口，并在 resources/META-INF/spring.factories 文件中注册。
    - Spring Boot 启动时会通过反射机制读取 spring.factories，实例化并调用这些监听器，但不会把它们作为 Bean 放入 IOC 容器。
    - 不能通过 ApplicationContext.getBean(...) 获取，否则会抛出 NoSuchBeanDefinitionException。
    - 这类监听器只能被 Spring Boot 启动流程自动调用，不能像普通 Bean 一样注入或通过容器获取。
    - 如果想让某个监听器既能参与 Spring Boot 启动流程，又能作为 Bean 注入，需要分别实现不同的机制（一般没必要这样做）。
*/
public class MyApplicationStartingListener implements SpringApplicationRunListener {
    public MyApplicationStartingListener(SpringApplication application, String[] args) {
        // 构造方法必须保留
    }

    @Override
    public void starting() {
        System.out.println("【监听器】Spring Boot 正在启动（starting）...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("【监听器】环境已准备好（environmentPrepared）...");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("【监听器】上下文已准备好（contextPrepared）...");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("【监听器】上下文已加载（contextLoaded）...");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("【监听器】Spring Boot 已启动（started）...");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("【监听器】Spring Boot 正在运行（running）...");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("【监听器】Spring Boot 启动失败（failed）...");
    }
} 