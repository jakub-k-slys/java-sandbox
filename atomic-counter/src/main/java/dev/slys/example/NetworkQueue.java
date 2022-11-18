package dev.slys.example;

import java.util.UUID;

public class NetworkQueue {
    public Event poll() {
        return new Event(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
    }
}
