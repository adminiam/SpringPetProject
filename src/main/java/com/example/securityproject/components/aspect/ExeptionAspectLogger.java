package com.example.securityproject.components.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExeptionAspectLogger {
    @Pointcut("execution(* com.example.securityproject.service.*.*(..))")
    public void loggingPointCut() {
    }

    @AfterThrowing(pointcut = "loggingPointCut()", throwing = "ex")
    public void logError(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        System.out.println("Exception in " + className + "." + methodName + "() with cause = " + ex.getMessage());
    }

}
