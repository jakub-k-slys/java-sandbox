package dev.slys.example;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Executors;

public class ThreadPool {

    private ThreadPool() {}

    public static ListeningExecutorService newFixedPool() {
        return MoreExecutors.listeningDecorator(
                Executors.newFixedThreadPool(1, runnable -> {
                    Thread thread = new Thread(runnable);
                    thread.setDaemon(true);
                    return thread;
                }));
    }
}
