package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleRestService implements RestService {

    private static final ListeningExecutorService POOL =
            MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));

    @Override
    public ListenableFuture<Results> execute() {
        return POOL.submit(this::callRestEndpoint);
    }
    private Results callRestEndpoint() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return new Results();
    }
}
