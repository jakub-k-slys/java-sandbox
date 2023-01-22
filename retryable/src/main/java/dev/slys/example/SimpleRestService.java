package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleRestService implements RestService {

    private static final ListeningExecutorService POOL = ThreadPool.newFixedPool();

    @Override
    public ListenableFuture<Results> execute() {
        return POOL.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            return new Results();
        });
    }
}
