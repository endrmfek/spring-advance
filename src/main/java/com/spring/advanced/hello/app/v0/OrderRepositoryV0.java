package com.spring.advanced.hello.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId) {
        if(itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000); //상품을 저장하는데 1초정도 걸림.
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
