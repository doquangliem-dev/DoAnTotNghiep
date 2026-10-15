package com.huit.kiemdinh.config;

import org.springframework.stereotype.Component;

@Component
public class DemoDataState {
    private volatile boolean ready = false;

    public boolean isReady() {
        return ready;
    }

    public void markReady() {
        this.ready = true;
    }

    public void markNotReady() {
        this.ready = false;
    }
}
