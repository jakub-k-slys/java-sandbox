package dev.slys.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Fork(1)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 20)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
public class ArrayListEnsureCapacityBenchmark {

    @Param({"100", "1000", "10000", "100000", "1000000"})
    public int no_of_elements;
    private ArrayList<Integer> ints;

    @Setup(Level.Iteration)
    public void setup_benchmark() {
        ints = new ArrayList<>();
    }

    @Benchmark
    @Threads(1)
    public void benchmark_no_ensure_capacity(Blackhole blackhole) {
        for (int i = 0; i < no_of_elements; ++i) {
            blackhole.consume(ints.add(i));
        }
    }

    @Benchmark
    @Threads(1)
    public void benchmark_ensure_capacity(Blackhole blackhole) {
        ints.ensureCapacity(no_of_elements);
        for (int i = 0; i < no_of_elements; ++i) {
            blackhole.consume(ints.add(i));
        }
    }
}
