package com.example.modern_java_in_action;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class StreamStart {

    @DisplayName("stream loop test")
    @Test
    void stream_is_only_one_search() {
        List<String> fruits = List.of("apple", "banana", "strawberry");
        Stream<String> fruitStream = fruits.stream();

        assertThatCode(() -> {
            System.out.println("스트림 첫 번째 사용");
            fruitStream.forEach(System.out::println);
        }).doesNotThrowAnyException();

        assertThatCode(() -> {
            System.out.println("스트림 두 번째 사용");
            fruitStream.forEach(System.out::println);
        }).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("stream loop test")
    @Test
    void stream_is_loop_fusion() {
        List<String> fruits = List.of("apple", "banana", "strawberry");
        Stream<String> fruitStream = fruits.stream();

        fruitStream
                .filter(f -> {
                    System.out.println("filtering " + f);
                    return true;
                })
                .map(f -> {
                    System.out.println("mapping " + f);
                    return f;
                })
                .toList();
    }

    @DisplayName("stream loop test")
    @Test
    void inner_iter_vs_external_iter() {
        List<String> fruits = List.of("apple", "banana", "strawberry");
        Stream<String> fruitStream = fruits.stream();

        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        fruits.stream().forEach(f -> System.out.println(f));
    }
}
