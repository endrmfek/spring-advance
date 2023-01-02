package com.spring.advanced.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //aop.order 패키지와 하위 패키지
    //메서드 반환 타입은 void여야 한다.
    //내부에서만 사용하면 private 사용해도 되지만, 다른 Aspect에서 참고하려면 public 해야한다.
    @Pointcut("execution(* com.spring.advanced.aop.order..*(..))")
    public void allOrder(){} // pointcut signature

    //클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    //allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
