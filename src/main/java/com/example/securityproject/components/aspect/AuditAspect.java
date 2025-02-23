package com.example.securityproject.components.aspect;

import com.example.securityproject.interfaces.Auditable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuditAspect {
    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @AfterReturning(value = "@annotation(auditable)", returning = "result")
    public void auditAction(JoinPoint joinPoint, Auditable auditable, Object result) {
        String action = auditable.action();
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Audit: action '{}', method '{}', result '{}'", action, methodName, result);

//todo add to the db
    }
}
