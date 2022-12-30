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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetryableQueryServiceTest {

    private static final Results RESULTS = new Results();
    @Mock
    QueryService queryService;

    RetryableQueryService retryableQueryService;

    @BeforeEach
    void setup() {
        retryableQueryService = new RetryableQueryService(queryService);
    }

    @Test
    void shouldCompleteSuccessfully() {
        when(queryService.execute())
                .thenReturn(Futures.immediateFuture(RESULTS));

        retryableQueryService.execute();

        verify(queryService, times(1)).execute();
    }

    @Test
    void shouldCompleteSuccessfullyWithRetries() throws ExecutionException, InterruptedException {
        when(queryService.execute())
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFuture(RESULTS));

        ListenableFuture<Results> future = retryableQueryService.execute();
        assertThat(future.get()).isEqualTo(RESULTS);
        verify(queryService, times(4)).execute();
    }

    @Test
    void shouldFailAfter4Retires() {
        when(queryService.execute())
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()))
                .thenReturn(Futures.immediateFailedFuture(new RuntimeException()));

        ListenableFuture<Results> future = retryableQueryService.execute();
        assertThatThrownBy(future::get);
        verify(queryService, times(4)).execute();
    }
}
