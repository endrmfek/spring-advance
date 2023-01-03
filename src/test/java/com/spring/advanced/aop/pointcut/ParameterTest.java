package com.spring.advanced.aop.pointcut;

import com.spring.advanced.aop.member.MemberService;
import com.spring.advanced.aop.member.annotation.ClassAop;
import com.spring.advanced.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}" , memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* com.spring.advanced.aop.member..*.*(..))")
        private void allMember() {}

        //파라미터값을 어떻게 넘길것인지.
        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] {} , arg={}", joinPoint.getSignature() , arg1);
            return joinPoint.proceed();
        }


        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {} , arg={}", joinPoint.getSignature() , arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] args = {}", arg);
        }

        @Before("allMember() && this(obj)") // 프록시 객체
        public void thisArgs(JoinPoint joinpoint , MemberService obj) {
            log.info("[this] {} , obj = {}", joinpoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(obj)") // 실제 대상 구현체
        public void targetArgs(JoinPoint joinpoint , MemberService obj) {
            log.info("[target] {} , obj = {}", joinpoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        public void atTargetArgs(JoinPoint joinpoint , ClassAop annotation) {
            log.info("[@target] {} , obj = {}", joinpoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithinArgs(JoinPoint joinpoint , ClassAop annotation) {
            log.info("[@within] {} , obj = {}", joinpoint.getSignature(), annotation);
        }

        @Before("allMember() && @annotation(annotation)")
        public void atWithinArgs(JoinPoint joinpoint , MethodAop annotation) {
            log.info("[@annotation] {} , obj = {}", joinpoint.getSignature(), annotation.value()); // 애노테이션 값을 꺼낼 수 있음.
        }


    }
}
