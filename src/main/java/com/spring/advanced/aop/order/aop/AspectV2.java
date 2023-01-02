package com.spring.advanced.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect // 이것만으로는 bean으로 설정되지 않아. 따로 bean등록 해줘야됨.
public class AspectV2 {

    //aop.order 패키지와 하위 패키지
    //메서드 반환 타입은 void여야 한다.
    //내부에서만 사용하면 private 사용해도 되지만, 다른 Aspect에서 참고하려면 public 해야한다.
    @Pointcut("execution(* com.spring.advanced.aop.order..*(..))")
    private void allOrder(){} // pointcut signature


    @Around("allOrder()") // 포인트 컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // adivce
        return joinPoint.proceed();
    }
}
