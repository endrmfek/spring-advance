package com.spring.advanced.proxy.config.v6_aop.aspect;

import com.spring.advanced.trace.TraceStatus;
import com.spring.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* com.spring.advanced.proxy.app..*(..))") //포인트컷
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        //advice 로직
        TraceStatus status = null;
        try{
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            //target호출 , 로직호출
//            Object result = method.invoke(target, args);
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    } // advisor가 됨.


    /**
     * 런타임 시점. 자바가 실행되고 난 다음을 말한다.
     * 컨테이너, 프록시 , DI, 빈 포스트 프로세서 다 동원해ㅑㅇ돼
     * 프록시 방식의 AOP.
     *
     * AOP를 적용 할 지점  조인 포인트
     * 스프링 빈에만 적용할 수 있다.
     * 스프링 빈에 등록해야돼
     *
     * 스프링이 제공하는 AOP는 프록시를 사용한다. 따라서 프록시를 통해 메서드를 실해앟는 시점에만 AOP가 적용된다.
     * ASPECTJ를 사용하면 앞서 설명한 것처럼 더 복잡하고 다양한 기능을 사용할 수 있다.
     *
     * */
}
