// MethodCallCounterAspect.java
package com.fei.aop.count_method_call_count;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@AllArgsConstructor
public class MethodCallCounterAspect {

    private final ApplicationEventPublisher eventPublisher;

    @Around("@annotation(com.fei.aop.count_method_call_count.MethodCallCount)")
    public Object countMethodCall(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        try {
            return pjp.proceed();
        } finally {
            eventPublisher.publishEvent(method.getName());
            // 避免用这个，每次都 new，如果并发量比较高的话，就有点浪费内存了
//            eventPublisher.publishEvent(new MethodCallEvent(this, method.getName()));
        }
    }
}