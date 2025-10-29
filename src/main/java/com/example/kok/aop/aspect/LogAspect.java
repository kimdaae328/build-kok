package com.example.kok.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Configuration
@Slf4j
public class LogAspect {
    @Around("logStatusAnnotated()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("{}: {}", joinPoint.getSignature().getName(),
                Arrays.stream(joinPoint.getArgs()).map(String::valueOf).collect(Collectors.joining(", ")));
        return joinPoint.proceed();
    }

    @AfterReturning(value = "logReturnStatusAnnotated()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        log.info("{}: {}", joinPoint.getSignature().getName(),
                Arrays.stream(joinPoint.getArgs()).map(String::valueOf).collect(Collectors.joining(", ")));
        log.info("return: {}", returnValue);
    }

    @Pointcut("@annotation(com.example.kok.aop.aspect.annotation.LogStatus)")
    public void logStatusAnnotated(){}

    @Pointcut("@annotation(com.example.kok.aop.aspect.annotation.LogReturnStatus)")
    public void logReturnStatusAnnotated(){}
}


