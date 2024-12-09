package com.presnakov.hotelbooking.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ServiceAspect {

    @Around("CommonPointcuts.isAnyServiceMethod() && target(service)")
    public Object addLogging(ProceedingJoinPoint joinPoint, Object service) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Invoked method {} in class {} with arguments: {}", methodName, service.getClass().getName(), Arrays.toString(args));
        try {
            Object result = joinPoint.proceed();
            log.info("Invoked method {} in class {} with returning result: {}", methodName, service.getClass().getName(), result);
            return result;
        } catch (Throwable ex) {
            log.error("Exception in method {} in class {} with arguments: {}", methodName, service.getClass().getName(), Arrays.toString(args), ex);
            throw ex;
        }
    }
}
