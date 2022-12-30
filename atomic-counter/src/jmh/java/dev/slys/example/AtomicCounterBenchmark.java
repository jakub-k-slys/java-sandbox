package dev.slys.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;

@Fork(1)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 20)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
public class AtomicCounterBenchmark {
    private final Counter counter = new Counter();

    @Benchmark
    @Threads(1)
    public void getCnt_singleThread(Blackhole blackhole){
        blackhole.consume(counter.getCnt());
    }

    @Benchmark
    @Threads(2)
    public void getCnt_twoThreads(Blackhole blackhole){
        blackhole.consume(counter.getCnt());
    }

    @Benchmark
    @Threads(4)
    public void getCnt_fourThreads(Blackhole blackhole){
        blackhole.consume(counter.getCnt());
    }

    @Benchmark
    @Threads(8)
    public void getCnt_eightThreads(Blackhole blackhole){
        blackhole.consume(counter.getCnt());
    }

    @Benchmark
    @Threads(1)
    public void inc_singleThread(Blackhole blackhole){
        counter.inc();
    }

    @Benchmark
    @Threads(2)
    public void inc_twoThreads(Blackhole blackhole){
        counter.inc();
    }

    @Benchmark
    @Threads(4)
    public void inc_fourThreads(Blackhole blackhole){
        counter.inc();
    }

    @Benchmark
    @Threads(8)
    public void inc_eightThreads(Blackhole blackhole){
        counter.inc();
    }
}
