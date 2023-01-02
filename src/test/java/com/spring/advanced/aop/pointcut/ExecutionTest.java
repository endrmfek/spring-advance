package com.spring.advanced.aop.pointcut;

import com.spring.advanced.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
//        public java.lang.String com.spring.advanced.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    //가장 정확한 표현식
    void exactMatch() {
        //public java.lang.String com.spring.advanced.aop.member.MemberServiceImpl.hello(java.lang.String)
        //execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        //선언타입 - 패키지명 + 클래스명
        //?는 생략가능
        pointcut.setExpression("execution(public String com.spring.advanced.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        //반환타입 , 메서드이름
        //파라미터에서 .. 은 파라미터 타입과 파라미터 수가 상관없다는 뜻이다.
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* com.spring.advanced.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* com.spring.advanced.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse() {
        pointcut.setExpression("execution(* com.spring.advanced.aop.*.*(..))"); // . 은 하위타입 포함 X
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        pointcut.setExpression("execution(* com.spring.advanced.aop..*.*(..))"); // .. 은 하위타입 포함 O
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* com.spring.advanced.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType() {
        pointcut.setExpression("execution(* com.spring.advanced.aop.member.MemberService.*(..))"); // 인터페이스를 조회하면?
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue(); // 부모타입으로 조회해도 자식타입은 매칭이 돼.
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* com.spring.advanced.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod , MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* com.spring.advanced.aop.member.MemberService.*(..))"); // 인터페이스에 없는 메소드 조회하면?
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod , MemberServiceImpl.class)).isFalse(); // 부모타입에 선언된 메서드만 돼..
    }

    //String 타입의 파라미터 허용 (String)
    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    //파라미터가 없어야함. ()
    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isFalse();
    }

    //정확히 하나의 파라미터 허용, 모든 타입 허용
    //(X xx)
    @Test
    void argsMatchStar() {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    //숫자와 무관하게 모든 파라미터, 모든 타입 허용
    //(), (X xx), (X xx , Y yy)
    @Test
    void argsMatchAll() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

    //String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    //(String), (String , X xx), (String , X xx , Y yy)
    @Test
    void argsMatchComplex() {
        pointcut.setExpression("execution(* *(String , ..))");
        assertThat(pointcut.matches(helloMethod , MemberServiceImpl.class)).isTrue();
    }

}
