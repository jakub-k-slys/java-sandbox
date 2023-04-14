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
    private static final int[] INTS_100;
    private static final int[] INTS_1000;
    private static final int[] INTS_10000;
    private static final int[] INTS_100000;
    private static final int[] INTS_1000000;
    private static final ArrayList<Integer> INTS_ARRAY_100;
    private static final ArrayList<Integer> INTS_ARRAY_1000;
    private static final ArrayList<Integer> INTS_ARRAY_10000;
    private static final ArrayList<Integer> INTS_ARRAY_100000;
    private static final ArrayList<Integer> INTS_ARRAY_1000000;

    private static int[] initInts(int cap) {
        int[] ints = new int [cap];
        for (int i = 0; i < cap; ++i) {
            ints[i] = i;
        }
        return ints;
    }

    private static ArrayList<Integer> initArrayList(int cap) {
        ArrayList<Integer> intsArray = new ArrayList<>(cap);
        for (int i = 0; i < cap; ++i) {
            intsArray.add(i, i);
        }
        return intsArray;
    }

    static {
        INTS_100 = initInts(100);
        INTS_1000 = initInts(1000);
        INTS_10000 = initInts(10000);
        INTS_100000 = initInts(100000);
        INTS_1000000 = initInts(1000000);

        INTS_ARRAY_100 = initArrayList(100);
        INTS_ARRAY_1000 = initArrayList(1000);
        INTS_ARRAY_10000 = initArrayList(10000);
        INTS_ARRAY_100000 = initArrayList(100000);
        INTS_ARRAY_1000000 = initArrayList(1000000);
    }



    private int sum(IntStream stream) {
        return stream.sum();
    }

    @Benchmark
    @Threads(1)
    public void benchmark_array_100(Blackhole blackhole) {
        blackhole.consume(sum(Arrays.stream(INTS_100)));
    }
    @Benchmark
    @Threads(1)
    public void benchmark_array_1000(Blackhole blackhole) {
        blackhole.consume(sum(Arrays.stream(INTS_1000)));
    }
    @Benchmark
    @Threads(1)
    public void benchmark_array_10000(Blackhole blackhole) {
        blackhole.consume(sum(Arrays.stream(INTS_10000)));
    }
    @Benchmark
    @Threads(1)
    public void benchmark_array_100000(Blackhole blackhole) {
        blackhole.consume(sum(Arrays.stream(INTS_100000)));
    }
    @Benchmark
    @Threads(1)
    public void benchmark_array_1000000(Blackhole blackhole) {
        blackhole.consume(sum(Arrays.stream(INTS_1000000)));
    }

    @Benchmark
    @Threads(1)
    public void benchmark_arraylist_100(Blackhole blackhole) {
        blackhole.consume(sum(INTS_ARRAY_100.stream().mapToInt(Integer::intValue)));
    }
    @Benchmark
    @Threads(1)
    public void benchmark_arraylist_1000(Blackhole blackhole) {
        blackhole.consume(sum(INTS_ARRAY_1000.stream().mapToInt(Integer::intValue)));
    }
    @Benchmark
    @Threads(1)
    public void benchmark_arraylist_10000(Blackhole blackhole) {
        blackhole.consume(sum(INTS_ARRAY_10000.stream().mapToInt(Integer::intValue)));
    }
    @Benchmark
    @Threads(1)
    public void benchmark_arraylist_100000(Blackhole blackhole) {
        blackhole.consume(sum(INTS_ARRAY_100000.stream().mapToInt(Integer::intValue)));
    }
    @Benchmark
    @Threads(1)
    public void benchmark_arraylist_1000000(Blackhole blackhole) {
        blackhole.consume(sum(INTS_ARRAY_1000000.stream().mapToInt(Integer::intValue)));
    }
}
