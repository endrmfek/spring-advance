package com.spring.advanced.trace.threadlocal;

import com.spring.advanced.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalService service = new ThreadLocalService();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            service.logic("userA");
        };

        Runnable userB = () -> {
            service.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
//        sleep(2000); // 동시성 문제 발생X
        sleep(100); // 동시성 문제 발생
        threadB.start();
        sleep(3000); // 메인 스레드 종료 대기
        log.info("main exit");

        //동시성문제 : 저장한 데이터와 조회한 데이터가 다른 문제.
        //여러 쓰레드가 동시에 같은 인스턴스의 필드값을 변경하면서 발생하는 문제
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
