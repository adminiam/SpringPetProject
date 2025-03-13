package com.example.securityproject.components.aspect;

import com.example.securityproject.annotations.Auditable;
import com.example.securityproject.models.Action;
import com.example.securityproject.repository.JpaActionsRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuditAspect {
    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);
    @Autowired
    private JpaActionsRepo jpaActionsRepo;

    @AfterReturning(value = "@annotation(auditable)", returning = "result")
    public void auditAction(JoinPoint joinPoint, Auditable auditable, Object result) {
        String action = auditable.action();
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Audit: action '{}', method '{}', result '{}'", action, methodName, result);
        jpaActionsRepo.save(Action.builder().action(action).methodName(methodName).result(result.toString()).build());
    }
}
