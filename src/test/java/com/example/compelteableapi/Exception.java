package com.example.compelteableapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Exception {

    @ParameterizedTest
    @ValueSource(strings = {"ex", "xuni"})
    void exceptionally(String words) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (words.equals("ex")) {
                throw new IllegalArgumentException("ex is not Valid");
            }
            return words;
        }).exceptionally(ex -> ex.getMessage());

        System.out.println(future.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ex", "xuni"})
    void handle(String words) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (words.equals("ex")) {
                throw new IllegalArgumentException("ex is not Valid");
            }
            return words;
        }).handle((result, ex) ->
                ex == null ?
                Thread.currentThread().getName() + " " + result :
                ex.getMessage());

        System.out.println(future.get());
    }
}
