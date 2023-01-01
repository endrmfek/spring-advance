package com.spring.advanced.proxy.config.v5_autoproxy;

import com.spring.advanced.proxy.config.AppV1Config;
import com.spring.advanced.proxy.config.AppV2Config;
import com.spring.advanced.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.spring.advanced.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    //beanpostprocessor 가 이미 스프링이 빈으로 등록해놓음.
//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        //pointcut -> 끼워넣을 위치
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*" , "order*" , "save*");
        //advice -> 끼워넣을 로직
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        //pointcut -> 끼워넣을 위치
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.spring.advanced.proxy.app..*(..))");
        //advice -> 끼워넣을 로직
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        //pointcut -> 끼워넣을 위치
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.spring.advanced.proxy.app..*(..)) && !execution(* com.spring.advanced.proxy.app..noLog(..))");
        //advice -> 끼워넣을 로직
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
