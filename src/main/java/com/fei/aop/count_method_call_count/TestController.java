// TestController.java
package com.fei.aop.count_method_call_count;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class TestController {

    private final DemoService demoService;
    private final MethodCallCounterService counterService;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return demoService.sayHello(name);
    }

    @GetMapping("/work")
    public String work() {
        return demoService.doWork();
    }

    @GetMapping("/stats")
    public Map<String, Integer> stats() {
        return counterService.getAllMethodCallCounts();
    }
}