package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello world!");
        RestService restService = new RetryableRestService(new SimpleRestService());
        ListenableFuture<Results> results = restService.execute();
        for(String s : results.get()) {
            System.out.println(s);
        }
    }

    private void processResponses(Future<Results> future) {
        try {
            Results results = future.get();
            compute(results);
        } catch (RuntimeException e) {
            // maybe retry?
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void compute(Results results) {
        // compute some staff
    }
}