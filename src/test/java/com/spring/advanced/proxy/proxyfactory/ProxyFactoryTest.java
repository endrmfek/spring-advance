package com.spring.advanced.proxy.proxyfactory;

import com.spring.advanced.proxy.common.advice.TimeAdvice;
import com.spring.advanced.proxy.common.service.ConcreteService;
import com.spring.advanced.proxy.common.service.ServiceImpl;
import com.spring.advanced.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); // 프록시 팩토리를 만들때 타겟을 넣는다.
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass= {}", proxy.getClass());

        proxy.save();
        //프록시 팩토리를 사용할때만 가능
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target); // 프록시 팩토리를 만들때 타겟을 넣는다.
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass= {}", proxy.getClass());

        proxy.call();
        //프록시 팩토리를 사용할때만 가능
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // 항상 cglib 기반으로 만들어짐.
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass= {}", proxy.getClass());

        proxy.save();
        //프록시 팩토리를 사용할때만 가능
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    }
}
