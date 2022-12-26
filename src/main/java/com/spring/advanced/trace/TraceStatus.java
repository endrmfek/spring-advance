package com.spring.advanced.trace;

public class TraceStatus { // 로그의 상태정보

    private TraceId traceId;
    private Long startTimeMs;
    private String message;

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs; //로그 시작 시간
        this.message = message; //시작시 사용한 메세지.
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartIdMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}
