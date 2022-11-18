package dev.slys.example;

public class Counter {
    private int cnt = 0;

    public synchronized int getCnt() {
        return cnt;
    }

    public synchronized void inc() {
        ++cnt;
    }
}