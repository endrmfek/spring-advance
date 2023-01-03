package com.spring.advanced.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    // 생성자로 주입받으면 V1이 생성되지도 않았는데 주입하려고함 -> 오류발생
    // 순환참조 문제도 발생할 수 있음. 그래서 이 경우엔 setter로 주입해야됨.
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) { // 생성이 끝나고 꽂아야됨.
        log.info("callServiceV1 setter = {}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
