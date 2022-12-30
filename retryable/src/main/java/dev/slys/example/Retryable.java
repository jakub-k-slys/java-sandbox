package dev.slys.example;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Retryable<V> {

    private static final ListeningExecutorService POOL = ThreadPool.newFixedPool();
    private final Callable<ListenableFuture<V>> callable;
    private final int startingBackoff;
    private final int backoffFactor;
    private final int maxBackoff;
    private final int numOfRetries;
    private final Class<? extends Throwable> exception;

    private Retryable(
            Callable<ListenableFuture<V>> callable,
            int startingBackoff,
            int backoffFactor,
            int maxBackoff,
            int numOfRetries,
            Class<? extends Throwable> exception) {
        this.callable = callable;
        this.startingBackoff = startingBackoff;
        this.backoffFactor = backoffFactor;
        this.maxBackoff = maxBackoff;
        this.numOfRetries = numOfRetries;
        this.exception = exception;
    }

    public ListenableFuture<V> run() {
        try {
            return runWithRetries(startingBackoff, numOfRetries);
        } catch (Exception e) {
            return Futures.immediateFailedFuture(e);
        }
    }

    private ListenableFuture<V> runWithRetries(int backoffTime, int retryCount) throws Exception {
        return Futures.catchingAsync(callable.call(), exception, ex -> {
            if (!(retryCount > 0)) {
                return Futures.immediateFailedFuture(ex);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(backoffTime);
                return runWithRetries(
                        Math.min(backoffTime * backoffFactor, maxBackoff),
                        retryCount - 1);
            } catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
                return Futures.immediateFailedFuture(e1);
            }
        }, POOL);
    }

    public static <R> Builder<R> from(Callable<ListenableFuture<R>> callable) {
        return new Builder<R>(callable);
    }
    static class Builder<V> {
        private final Callable<ListenableFuture<V>> callable;
        private static final int DEFAULT_STARTING_BACKOFF = 1;
        private static final int DEFAULT_BACKOFF_FACTOR = 2;
        private static final int DEFAULT_MAX_BACKOFF = 10;
        private static final int DEFAULT_NUM_OF_RETRIES = 3;
        private static final Class<? extends Throwable> DEFAULT_EXCEPTION = RuntimeException.class;

        private int startingBackoff = DEFAULT_STARTING_BACKOFF;
        private int backoffFactor = DEFAULT_BACKOFF_FACTOR;
        private int maxBackoff = DEFAULT_MAX_BACKOFF;
        private int numOfRetries = DEFAULT_NUM_OF_RETRIES;
        private Class<? extends Throwable> exception = DEFAULT_EXCEPTION;

        Builder(Callable<ListenableFuture<V>> callable) {
            this.callable = callable;
        }

        public Builder<V> withStartingBackoff(int startingBackoff) {
            this.startingBackoff = startingBackoff;
            return this;
        }

        public Builder<V> withBackoffFactor(int backoffFactor) {
            this.backoffFactor = backoffFactor;
            return this;
        }

        public Builder<V> withMaxBackoff(int maxBackoff) {
            this.maxBackoff = maxBackoff;
            return this;
        }

        public Builder<V> withNumOfRetries(int numOfRetries) {
            this.numOfRetries = numOfRetries;
            return this;
        }

        public Builder<V> withException(Class<? extends Throwable> exception) {
            this.exception = exception;
            return this;
        }

        public Retryable<V> build() {
            return new Retryable<V>(callable, startingBackoff, backoffFactor, maxBackoff, numOfRetries, exception);
        }
    }
}
