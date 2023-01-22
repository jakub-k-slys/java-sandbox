package dev.slys.example;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetryableRestService2Test {

    private static final Results RESULTS = new Results();
    @Mock
    RestService restService;

    RetryableRestService2 retryableQueryService;

    @BeforeEach
    void setup() {
        retryableQueryService = new RetryableRestService2(restService);
    }

    @Test
    void shouldCompleteSuccessfully() {
        when(restService.execute())
                .thenReturn(Futures.immediateFuture(RESULTS));

        retryableQueryService.execute();

        verify(restService, times(1)).execute();
    }

    @Test
    void shouldCompleteSuccessfullyWithRetries() throws ExecutionException, InterruptedException {
        when(restService.execute())
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFuture(RESULTS));

        ListenableFuture<Results> future = retryableQueryService.execute();
        assertThat(future.get()).isEqualTo(RESULTS);
        verify(restService, times(4)).execute();
    }

    @Test
    void shouldFailAfter6Retires() {
        when(restService.execute())
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()));

        ListenableFuture<Results> future = retryableQueryService.execute();
        assertThatThrownBy(future::get);
        verify(restService, times(6)).execute();
    }
}
