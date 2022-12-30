package dev.slys.example;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.concurrent.TimeUnit;

public class RetryableQueryService2 implements QueryService {

    private final QueryService delegate;


    RetryableQueryService2(QueryService service) {
        this.delegate = service;
    }

    @Override
    public ListenableFuture<Results> execute() {
        Retryable<Results> retryable = Retryable.from(delegate::execute)
                .withNumOfRetries(5)
                .build();
        return retryable.run();
    }
}
