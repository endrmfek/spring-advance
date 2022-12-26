package com.spring.advanced.hello.app.v2;

import com.spring.advanced.trace.TraceStatus;
import com.spring.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) { //파라미터 -> localhost:8080/v1/request?itemId=40


        TraceStatus status = null; // catch 스코프때문에..
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId() , itemId); // 예외터지면 end까지 안내려감.
            trace.end(status);
            return "Ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }

    }
}
