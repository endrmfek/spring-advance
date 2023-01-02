package com.spring.advanced.aop.member;

import com.spring.advanced.aop.member.annotation.ClassAop;
import com.spring.advanced.aop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "OK";
    }

    public String internal(String param) {
        return "ok";
    }
}
