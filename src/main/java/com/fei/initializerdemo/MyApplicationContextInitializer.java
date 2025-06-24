package com.fei.initializerdemo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 自定义初始化器Demo
 * 1. 目录结构
 *     新建了 com.fei.listenerdemo 包
 *     创建了 MyApplicationContextInitializer 类，实现了 ApplicationContextInitializer，演示初始化器的用法
 *     在 resources/META-INF/spring.factories 注册了该初始化器
 * 2. 你启动Spring Boot项目时会看到什么效果？
 *     控制台会输出如下内容：
 *         【初始化器】执行自定义初始化逻辑...
 * 3. 这个初始化器有什么用？
 *     可以在Spring容器刷新之前，对ConfigurableApplicationContext做自定义设置，比如注入属性、环境变量、打印信息等。
 * 4. 实际工程中什么时候用？
 *     - 动态注入环境变量或属性
 *     - 预先注册一些BeanDefinition
 *     - 结合多环境配置做初始化
 *     - 监控或埋点
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("【初始化器】执行自定义初始化逻辑...");
        // 你可以在这里做更多自定义操作，比如：
        // applicationContext.getEnvironment().setActiveProfiles("dev");
    }
} 