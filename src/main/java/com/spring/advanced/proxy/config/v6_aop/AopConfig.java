package com.spring.advanced.proxy.config.v6_aop;


import com.spring.advanced.proxy.config.AppV1Config;
import com.spring.advanced.proxy.config.AppV2Config;
import com.spring.advanced.proxy.config.v6_aop.aspect.LogTraceAspect;
import com.spring.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class , AppV2Config.class})
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace); //포인트컷 , 어드바이스로 전환이 됨.
    }
}
