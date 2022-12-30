package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello world!");
        QueryService queryService = new RetryableQueryService(new SimpleQueryService());
        ListenableFuture<Results> results = queryService.execute();
        for(String s : results.get()) {
            System.out.println(s);
        }
    }
}