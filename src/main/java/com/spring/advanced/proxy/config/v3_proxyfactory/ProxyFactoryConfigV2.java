package com.spring.advanced.proxy.config.v3_proxyfactory;

import com.spring.advanced.proxy.app.v1.*;
import com.spring.advanced.proxy.app.v2.OrderControllerV2;
import com.spring.advanced.proxy.app.v2.OrderRepositoryV2;
import com.spring.advanced.proxy.app.v2.OrderServiceV2;
import com.spring.advanced.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.spring.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {


    @Bean
    public OrderControllerV2 orderController(LogTrace logTrace) {
        OrderControllerV2 orderControllerV2 = new OrderControllerV2(orderService(logTrace));
        ProxyFactory factory = new ProxyFactory(orderControllerV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderControllerV2 proxy = (OrderControllerV2) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass() , orderControllerV2.getClass());
        return proxy;
    }


    @Bean
    public OrderServiceV2 orderService(LogTrace logTrace) {
        OrderServiceV2 orderServiceV2 = new OrderServiceV2(orderRepository(logTrace));
        ProxyFactory factory = new ProxyFactory(orderServiceV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderServiceV2 proxy = (OrderServiceV2) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass() , orderServiceV2.getClass());
        return proxy;
    }

    @Bean
    public OrderRepositoryV2 orderRepository(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();
        ProxyFactory factory = new ProxyFactory(orderRepositoryV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderRepositoryV2 proxy = (OrderRepositoryV2) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass() , orderRepositoryV2.getClass());
        return proxy;
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        //pointcut -> 끼워넣을 위치
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*" , "order*" , "save*");
        //advice -> 끼워넣을 로직
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
