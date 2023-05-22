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
public class ArrayVsArrayListBenchmark {

    @Param({"4", "16", "64", "256", "1024", "4096", "16384", "65536", "262144", "1048576", "4194304", "10000000"})
    public int no_of_elements;
    private static final int MAX = 10000000;
    private static final int[] INTS_ARRAY = new int[MAX];
    private static final ArrayList<Integer> INTS_ARRAY_LIST = new ArrayList<>(MAX);

    static {
        for (int i = 0; i < MAX; ++i) {
            INTS_ARRAY[i] = i;
            INTS_ARRAY_LIST.add(i);
        }
    }

    private int sumArray(int[] array) {
        int acc = 0;
        for (int i = 0; i< no_of_elements; ++i) {
            acc += array[i];
        }
        return acc;
    }

    private int sumArrayList(ArrayList<Integer> arrayList) {
        int acc = 0;
        for (int i = 0; i < no_of_elements; ++i) {
            acc += arrayList.get(i);
        }
        return acc;
    }

    @Benchmark
    @Threads(1)
    public void benchmark_array(Blackhole blackhole) {
        blackhole.consume(sumArray(INTS_ARRAY));
    }

    @Benchmark
    @Threads(1)
    public void benchmark_arraylist(Blackhole blackhole) {
        blackhole.consume(sumArrayList(INTS_ARRAY_LIST));
    }
}
