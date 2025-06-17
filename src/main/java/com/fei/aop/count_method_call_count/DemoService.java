// DemoService.java
package com.fei.aop.count_method_call_count;

import org.springframework.stereotype.Service;

@Service
public class DemoService {
    @MethodCallCount
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }

    @MethodCallCount
    public String doWork() {
        try {
            Thread.sleep(100); // 模拟耗时操作
        } catch (InterruptedException ignored) {}
        return "Work done!";
    }

    // 这个方法不会被统计
    public String notCountedMethod() {
        return "This call won't be counted";
    }
}