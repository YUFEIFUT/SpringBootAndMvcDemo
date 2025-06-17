// MethodCallCounterService.java
package com.fei.aop.count_method_call_count;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MethodCallCounterService {
    private final Map<String, AtomicInteger> methodCallCountMap = new ConcurrentHashMap<>();

    @EventListener
    public void handleMethodCallEvent(String methodName) {
        methodCallCountMap.computeIfAbsent(methodName, k -> new AtomicInteger(0))
                         .incrementAndGet();
    }

    public int getMethodCallCount(String methodName) {
        AtomicInteger counter = methodCallCountMap.get(methodName);
        return counter == null ? 0 : counter.get();
    }

    public Map<String, Integer> getAllMethodCallCounts() {
        Map<String, Integer> result = new ConcurrentHashMap<>();
        methodCallCountMap.forEach((k, v) -> result.put(k, v.get()));
        return result;
    }
}