package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;

public class RetryableRestService2 implements RestService {

    private final RestService delegate;


    RetryableRestService2(RestService service) {
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
