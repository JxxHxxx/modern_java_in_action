package com.example.compelteableapi;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Execute {

    /**
     * 비동기 작업 실행
     * 반환 값이 존재하는 경우
     */
    @Test
    void supply_async() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "Thread: " + Thread.currentThread().getName());

        System.out.println(future.get());
    }

    /**
     * 비동기 작업 실행
     * 반환 값이 없는 경우
     */
    @Test
    void run_async() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture
                .runAsync(() -> System.out.println("Thread: " + Thread.currentThread().getName()));

        future.get();
    }
}
