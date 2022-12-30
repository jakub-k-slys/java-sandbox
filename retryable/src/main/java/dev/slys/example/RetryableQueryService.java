package dev.slys.example;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.concurrent.TimeUnit;

public class RetryableQueryService implements QueryService {

    private static final ListeningExecutorService POOL = ThreadPool.newFixedPool();
    private final QueryService delegate;

    private static final int STARTING_BACKOFF = 1;
    private static final int BACKOFF_FACTOR = 2;
    private static final int MAX_BACKOFF = 10;
    private static final int NUM_OF_RETRIES = 3;

    RetryableQueryService(QueryService service) {
        this.delegate = service;
    }

    @Override
    public ListenableFuture<Results> execute() {
        return executeWithRetry(STARTING_BACKOFF, NUM_OF_RETRIES);
    }

    private ListenableFuture<Results> executeWithRetry(int backoffTime, int retryCount) {
        return Futures.catchingAsync(delegate.execute(), RuntimeException.class, exception -> {
            if (retryCount > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(backoffTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return Futures.immediateFailedFuture(exception);
                }
                int newBackoffTime = Math.min(backoffTime * BACKOFF_FACTOR, MAX_BACKOFF);
                int newRetryCount = retryCount - 1;
                return executeWithRetry(newBackoffTime, newRetryCount);
            }
            return Futures.immediateFailedFuture(exception);
        }, POOL);
    };
}
