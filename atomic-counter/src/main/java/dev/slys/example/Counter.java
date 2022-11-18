package dev.slys.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private final AtomicInteger cnt = new AtomicInteger();

    public int getCnt() {
        return cnt.get();
    }

    public void inc() {
        cnt.incrementAndGet();
    }
}