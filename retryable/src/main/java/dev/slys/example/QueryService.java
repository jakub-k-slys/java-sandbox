package dev.slys.example;

import com.google.common.util.concurrent.ListenableFuture;

interface QueryService {

    ListenableFuture<Results> execute();
}
