package com.spring.advanced.proxy.config.v1_proxy.concrete_proxy;

import com.spring.advanced.proxy.app.v2.OrderRepositoryV2;
import com.spring.advanced.trace.TraceStatus;
import com.spring.advanced.trace.logtrace.LogTrace;

public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {

    private final OrderRepositoryV2 target; // 실제 호출할 객체
    private final LogTrace logTrace;

    public OrderRepositoryConcreteProxy(OrderRepositoryV2 target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try{
            status = logTrace.begin("OrderRepository.save()");

            //target호출
            target.save(itemId);

            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
