package com.spring.advanced;

import com.spring.advanced.trace.logtrace.LogTrace;
import com.spring.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;

//@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() { // 싱글톤으로 관리됩니다!
        return new ThreadLocalLogTrace();
    }
}
