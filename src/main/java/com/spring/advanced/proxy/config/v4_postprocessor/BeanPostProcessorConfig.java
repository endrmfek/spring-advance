package com.spring.advanced.proxy.config.v4_postprocessor;

import com.spring.advanced.proxy.config.AppV1Config;
import com.spring.advanced.proxy.config.AppV2Config;
import com.spring.advanced.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.spring.advanced.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import com.spring.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class,AppV2Config.class})
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
        return new PackageLogTracePostProcessor("com.spring.advanced.proxy.app", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        //pointcut -> 끼워넣을 위치
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*" , "order*" , "save*");
        //advice -> 끼워넣을 로직
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
