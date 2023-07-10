package com.example.modern_java_in_action.chapter5;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class InfiniteStream {

    /**
     * 끝이 없는 스트림을 무한 스트림, 언바운드 스트림이라고 한다.
     */
    @Test
    void iterate() {
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    void iterateWithPredicate() {
        Stream.iterate(0, n -> n < 100, n -> n + 2)
                .forEach(System.out::println);
    }

    @Test
    void iterateWithShortCircuit() {
        Stream.iterate(0, n -> n + 2)
                .takeWhile(n -> n < 100)
                .forEach(System.out::println);
    }

    @Test
    void generate() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }
}
