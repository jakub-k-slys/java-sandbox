package dev.slys.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Fork(1)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
public class ArrayListEnsureCapacityBenchmark {

    @Param({"4", "16", "64", "256", "1024", "4096", "16384", "65536", "262144", "1048576", "4194304", "10000000"})
    public int no_of_elements;

    @Benchmark
    @Threads(1)
    public void benchmark_no_ensure_capacity(Blackhole blackhole) {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < no_of_elements; ++i) {
            blackhole.consume(ints.add(i));
        }
    }

    @Benchmark
    @Threads(1)
    public void benchmark_ensure_capacity(Blackhole blackhole) {
        ArrayList<Integer> ints = new ArrayList<>();
        ints.ensureCapacity(no_of_elements);
        for (int i = 0; i < no_of_elements; ++i) {
            blackhole.consume(ints.add(i));
        }
    }
}
