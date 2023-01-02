package com.spring.advanced.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect // 이것만으로는 bean으로 설정되지 않아. 따로 bean등록 해줘야됨.
public class AspectV1 {

    @Around("execution(* com.spring.advanced.aop.order..*(..))") // 포인트 컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // adivce
        return joinPoint.proceed();
    }
}
