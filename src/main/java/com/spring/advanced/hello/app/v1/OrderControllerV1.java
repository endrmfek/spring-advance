package com.spring.advanced.hello.app.v1;

import com.spring.advanced.trace.TraceStatus;
import com.spring.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId) { //파라미터 -> localhost:8080/v1/request?itemId=40

        //version1.
//        TraceStatus status = trace.begin("OrderController.request()");
//        orderService.orderItem(itemId); // 예외터지면 end까지 안내려감.
//        trace.end(status);

        //version2.
        TraceStatus status = null; // catch 스코프때문에..
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId); // 예외터지면 end까지 안내려감.
            trace.end(status);
            return "Ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }

    }
}
