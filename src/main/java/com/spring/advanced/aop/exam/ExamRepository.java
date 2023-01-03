package com.spring.advanced.aop.exam;

import com.spring.advanced.aop.exam.annotation.Retry;
import com.spring.advanced.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * 5번에 1번 실패하는 요청
     * */
    @Trace
    @Retry(value = 4) // 횟수 제한이 무조건 있어야대. 없으면 DDOS가 됨.
    public String save(String itemId) {
        seq++;
        if(seq % 5 == 0) {
            throw new IllegalStateException("예외 발생");
        }
        return "OK";
    }

}
