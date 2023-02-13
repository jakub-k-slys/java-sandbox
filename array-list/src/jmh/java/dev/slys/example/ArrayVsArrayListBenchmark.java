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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Fork(1)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 20)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
public class ArrayVsArrayListBenchmark {
    private static final int[] INTS;
    private static final ArrayList<Integer> INTS_ARRAY;

    static {
        INTS = new int [1000];
        INTS_ARRAY = new ArrayList<>(1000);
        for (int i = 0; i < 1000; ++i) {
            INTS[i] = i;
            INTS_ARRAY.add(i, i);
        }
    }

    private int sum(IntStream stream) {
        return stream.sum();
    }

    @Benchmark
    @Threads(1)
    public void benchmark_array(Blackhole blackhole) {
        blackhole.consume(sum(Arrays.stream(INTS)));
    }

    @Benchmark
    @Threads(1)
    public void benchmark_arraylist(Blackhole blackhole) {
        blackhole.consume(sum(INTS_ARRAY.stream().mapToInt(Integer::intValue)));
    }
}
