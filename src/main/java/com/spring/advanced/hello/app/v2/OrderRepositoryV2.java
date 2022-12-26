package com.spring.advanced.hello.app.v2;

import com.spring.advanced.trace.TraceId;
import com.spring.advanced.trace.TraceStatus;
import com.spring.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final HelloTraceV2 trace;

    public void save(TraceId traceId, String itemId) {

        TraceStatus status = null; // catch 스코프때문에..
        try {
            status = trace.beginSync(traceId , "OrderRepository.save()");
            //로직
            if(itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000); //상품을 저장하는데 1초정도 걸림.
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }


    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
