package com.spring.advanced.aop;

import com.spring.advanced.aop.order.OrderRepository;
import com.spring.advanced.aop.order.OrderService;
import com.spring.advanced.aop.order.aop.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
//@Import(AspectV1.class) // isAopProxy = true
//@Import(AspectV2.class) // isAopProxy = true
//@Import(AspectV3.class) // isAopProxy = true
//@Import(AspectV4Pointcut.class) // isAopProxy = true
//@Import({AspectV5Order.LogAspect.class , AspectV5Order.TxAspect.class}) // isAopProxy = true
@Import(AspectV6Advice.class) // isAopProxy = true
class AopTest { // scanBasePackage 잘 보고 사용할 것

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void appInfo() {
        log.info("isAopProxy , orderService = {}" , AopUtils.isAopProxy(orderService));
        log.info("isAopProxy , orderRepository = {}" , AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }
}