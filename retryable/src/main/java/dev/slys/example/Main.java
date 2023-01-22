package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello world!");
        RestService restService = new RetryableRestService(new SimpleRestService());
        ListenableFuture<Results> results = restService.execute();
        for(String s : results.get()) {
            System.out.println(s);
        }
    }
}