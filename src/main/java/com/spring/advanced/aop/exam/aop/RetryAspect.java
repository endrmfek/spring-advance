package com.spring.advanced.aop.exam.aop;

import com.spring.advanced.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class RetryAspect {

//    @Around("@annotation(com.spring.advanced.aop.exam.annotation.Retry)")
    @Around("@annotation(retry)") // 메서드의 타입정보가 요기로 들어감
    public Object doRetry(ProceedingJoinPoint joinPoint , Retry retry) throws Throwable {
        log.info("[retry] {} retry = {}" , joinPoint.getSignature() , retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for(int i=0; i<maxRetry; i++) {
            try {
                log.info("[retry] try count = {} / {}", i, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}
