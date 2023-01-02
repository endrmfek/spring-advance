package com.spring.advanced.aop.pointcut;

import com.spring.advanced.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    //가장 정확한 표현식
    void withinExact() {
        pointcut.setExpression("within(com.spring.advanced.aop.member.MemberServiceImpl)");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within(com.spring.advanced.aop.member.*Service*)");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(com.spring.advanced.aop.member..*)");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    /**
    * 타입이 정확하게 매칭되어야함. 부모타입을 지정하면 안돼.
    * */

    @Test
    @DisplayName("타겟의 타입에만 직접 적용, 인터페이스를 선정하면 안됨.")
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(com.spring.advanced.aop.member.MemberService)");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("execution은 타입 기반, 인터페이스 선정 가능")
    void executionSuperTypeFalse() {
        pointcut.setExpression("execution(* com.spring.advanced.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

}
