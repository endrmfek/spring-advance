package com.spring.advanced.aop.proxyvs;

import com.spring.advanced.aop.member.MemberService;
import com.spring.advanced.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class = false"}) // JDK 동적프록시
@SpringBootTest(properties = {"spring.aop.proxy-target-class = true"}) // cglib 프록시
@Import(ProxyDiTest.class)
public class ProxyDiTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl; // JDK 동적프록시에서 주입이 안돼. but cglib에선 됨.

    @Test
    void go() {
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberServiceImpl class = {}" , memberServiceImpl.getClass()); // JDK 동적프록시에서 주입이 안돼. but cglib에선 됨.
        memberServiceImpl.hello("hello");
    }
}
