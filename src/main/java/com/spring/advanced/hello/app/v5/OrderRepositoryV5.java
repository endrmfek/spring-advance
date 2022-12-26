package com.spring.advanced.hello.app.v5;

import com.spring.advanced.trace.callback.TraceTemplate;
import com.spring.advanced.trace.logtrace.LogTrace;
import com.spring.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV5 {

    private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId) {

        template.execute("OrderRepository.save()" , () -> {
            if(itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000); //상품을 저장하는데 1초정도 걸림.
            return null;
        });

    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
