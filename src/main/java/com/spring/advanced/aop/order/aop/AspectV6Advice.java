package com.spring.advanced.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    //aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    /*@Around("com.spring.advanced.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            //@Before
            log.info("[트랙잭션 시작] {}" , joinPoint.getSignature());
            Object result = joinPoint.proceed();
            //@AfterReturning
            log.info("[트랙잭션 종료] {}" , joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[트랙잭션 롤백] {}" , joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[리소스 릴리즈] {}" , joinPoint.getSignature());
        }
    }*/

    @Before("com.spring.advanced.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinpoint) { // 매개변수 형 잘봐라.
        log.info("[before] {}" , joinpoint.getSignature());
    }

    @AfterReturning(value= "com.spring.advanced.aop.order.aop.Pointcuts.orderAndService()" , returning = "result")
    public void deReturn(JoinPoint joinPoint , Object result) { // returning 이름이랑 연동되서 Object에 할당됨
        log.info("[return] {} , return = {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "com.spring.advanced.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) { // Exception이랑 throwing이랑 이름 맞춰줘야돼.
        log.info("[ex] {} message = {}" , joinPoint.getSignature() , ex);
    }

    @After("com.spring.advanced.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }



}
