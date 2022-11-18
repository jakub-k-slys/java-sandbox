package dev.slys.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static final NetworkQueue queue = new NetworkQueue();
    private static final EventProcessor processor = new EventProcessor();
    private static final Counter counter = new Counter();
    private static final int noOfThreads = 4;
    private static final ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int i = 10000;

        Runnable runnable = () -> {
            Event event = queue.poll();
            counter.inc();
            processor.handle(event);
        };

        Collection<Future<?>> futures = new ArrayList<>();
        while (i > 0) {
            futures.add(executor.submit(runnable));
            i--;
        }

        for (var future : futures) {
            future.get();
        }

        executor.shutdown();
        System.out.println("Processed events: " + counter.getCnt());
    }
}