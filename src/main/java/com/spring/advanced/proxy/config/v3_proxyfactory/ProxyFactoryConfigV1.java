package com.spring.advanced.proxy.config.v3_proxyfactory;

import com.spring.advanced.proxy.app.v1.*;
import com.spring.advanced.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import com.spring.advanced.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.spring.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.JavaBean;
import java.lang.reflect.Proxy;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {


    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderService(logTrace));
        ProxyFactory factory = new ProxyFactory(orderControllerV1);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderControllerV1 proxy = (OrderControllerV1) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass() , orderControllerV1.getClass());
        return proxy;
    }


    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepository(logTrace));
        ProxyFactory factory = new ProxyFactory(orderServiceV1);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderServiceV1 proxy = (OrderServiceV1) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass() , orderServiceV1.getClass());
        return proxy;
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryV1 = new OrderRepositoryV1Impl();
        ProxyFactory factory = new ProxyFactory(orderRepositoryV1);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderRepositoryV1 proxy = (OrderRepositoryV1) factory.getProxy();
        log.info("ProxyFactory proxy = {} , target = {}" , proxy.getClass() , orderRepositoryV1.getClass());
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
