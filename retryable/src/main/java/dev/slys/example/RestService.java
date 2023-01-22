package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;

interface RestService {

    ListenableFuture<Results> execute();
}
