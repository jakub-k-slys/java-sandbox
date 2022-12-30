package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;
import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.Callable;

public class Retryable {

    private Retryable() {}

    public static Builder from(Callable<ListenableFuture<?>> callable) {
        return new Builder(callable);
    }
    static class Builder {
        private Callable<ListenableFuture<?>> callable;
        private static final int DEFAULT_STARTING_BACKOFF = 1;
        private static final int DEFAULT_BACKOFF_FACTOR = 2;
        private static final int DEFAULT_MAX_BACKOFF = 10;
        private static final int DEFAULT_NUM_OF_RETRIES = 3;

        Builder(Callable<ListenableFuture<?>> callable) {
            this.callable = callable;
        }

        public Retryable build() {
            return new Retryable();
        }
    }
}
