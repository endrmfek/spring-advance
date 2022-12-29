package com.spring.advanced.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}" , result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}" , result1);
//        공통 로직2 종료
    }

    @Test
    void reflection1() throws Exception {
        //클래스 정보
        Class<?> classHello = Class.forName("com.spring.advanced.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target); // target에 있는 메소드A를 호출하겠다.
        log.info("result1 = {}" , result1);

        //callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallA.invoke(target); // target에 있는 메소드A를 호출하겠다.
        log.info("result2 = {}" , result2);
    }


    @Test
    void reflection2() throws Exception {
        //클래스 정보
        Class<?> classHello = Class.forName("com.spring.advanced.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA , target); // target에 있는 메소드A를 호출하겠다.

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB , target); // target에 있는 메소드B를 호출하겠다.
    }

    private void dynamicCall(Method method, Object target) throws Exception{
        //공통 로직1 시작
        log.info("start");
        Object result1 = method.invoke(target);
        log.info("result={}" , result1);
        //공통 로직1 종료
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
