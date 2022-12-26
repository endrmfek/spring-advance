package com.spring.advanced.hello.app.v5;

import com.spring.advanced.trace.callback.TraceCallback;
import com.spring.advanced.trace.callback.TraceTemplate;
import com.spring.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {

        template.execute("OrderService.orderItem()" , new TraceCallback<>() {
            @Override
            public Void call() {
                orderRepository.save(itemId);
                return null;
            }
        });

    }
}
