package com.presnakov.hotelbooking.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CommonPointcuts {

    @Pointcut("execution(public * com.presnakov.hotelbooking.service.*Service.*(..))")
    public void isAnyServiceMethod() {
    }
}