package com.fastjack.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.concurrent.atomic.AtomicLong;

@Component
@RequestScope// proxyMode = ScopedProxyMode.TARGET_CLASS is the default
public class TestScopeClass {

    private static final AtomicLong COUNTER = new AtomicLong(0);

    private final Long index;

    public TestScopeClass() {
        index = COUNTER.getAndIncrement();
        System.out.println("constructor invocation:" + index);
    }

    public long getCounter() {
        return index;
    }
}
