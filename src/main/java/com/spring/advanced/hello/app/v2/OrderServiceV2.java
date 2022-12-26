package com.spring.advanced.hello.app.v2;

import com.spring.advanced.trace.TraceId;
import com.spring.advanced.trace.TraceStatus;
import com.spring.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {

        TraceStatus status = null; // catch 스코프때문에..
        try {
            status = trace.beginSync(traceId , "OrderService.orderItem()");
            orderRepository.save(status.getTraceId() , itemId);
            trace.end(status);

        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }

    }

}
