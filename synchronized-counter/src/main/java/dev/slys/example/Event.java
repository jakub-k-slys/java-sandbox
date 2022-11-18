package dev.slys.example;

public class Event {
    private final String name;
    private final String content;

    Event(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
