package dev.slys.example;

public class EventProcessor {
    public void handle(Event e) {
        System.out.println(e.getName());
        System.out.println(e.getContent());
    }
}
