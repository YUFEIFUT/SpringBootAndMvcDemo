package com.fei.listenerdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 是 Spring Boot 提供的一个接口。
 * 只要容器中存在该类型的 Bean，Spring Boot 应用启动并完成所有 Bean 初始化后，
 * 会在主类的 run 方法中调用 callRunners(context, applicationArguments) 时自动执行其 run 方法。
 * 这是应用完全启动后进行缓存预热等初始化操作的一个时机。
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    /**
     * 执行时机：Spring Boot 应用启动并完成所有 Bean 初始化后，
     * 在 callRunners(context, applicationArguments) 方法中被调用。
     * 适合做缓存预热等操作。
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n【CommandLineRunner】此时SpringBoot已启动并完成所有Bean的初始化，run方法被调用。可以在这里做缓存预热等操作，这也是缓存预热的一个时机。\n");
    }
} 