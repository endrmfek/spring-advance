package com.spring.advanced.hello.app.v4;

import com.spring.advanced.trace.TraceStatus;
import com.spring.advanced.trace.logtrace.LogTrace;
import com.spring.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

    private final LogTrace trace;

    public void save(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                if(itemId.equals("ex")) {
                    throw new IllegalStateException("예외 발생!");
                }
                sleep(1000); //상품을 저장하는데 1초정도 걸림.
                return null;
            }
        };
        template.execute("OrderRepository.save()");
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
