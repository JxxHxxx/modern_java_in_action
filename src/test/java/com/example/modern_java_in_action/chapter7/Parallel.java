package com.example.modern_java_in_action.chapter7;

import org.junit.jupiter.api.Test;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/** 병렬 스트림
 * 각각의 스레드에서 처리할 수 있도록 스트림 요소를 여러 청크로 분할한 스트림이다.
 */

public class Parallel {
    private static final long ITERATION = 10000000L;
    @Test
    void sequentialSum() {

        long start = System.currentTimeMillis();
        System.out.println("sum " + sum(10000));
        long end = System.currentTimeMillis();
        System.out.println("task : " + (end -start));

        long start2 = System.currentTimeMillis();
        System.out.println("sum " + parallelSum(10000));
        long end2 = System.currentTimeMillis();
        System.out.println("task : " + (end2 -start2));
    }

    @Test
    void ranged() {
        long start = System.nanoTime();
        System.out.println("sum " + rangedSum());
        long end = System.nanoTime();
        System.out.println("task : " + (end -start));

        long start2 = System.nanoTime();
        System.out.println("sum " + parallelSum());
        long end2 = System.nanoTime();
        System.out.println("task : " + (end2 -start2));
    }

    private static long rangedSum() {
        return LongStream.rangeClosed(1, ITERATION)
                .reduce(0L, Long::sum);
    }

    private static long parallelSum() {
        return LongStream.rangeClosed(1, ITERATION)
                .parallel()
                .reduce(0L, Long::sum);
    }

    private static long sum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }
    private static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

}
