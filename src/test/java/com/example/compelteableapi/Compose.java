package com.example.compelteableapi;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

public class Compose {

    /** thenCompose
     *
     */
    @Test
    void then_compose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> currentThread = CompletableFuture
                .supplyAsync(() -> "Thread: " + Thread.currentThread().getName());

        CompletableFuture<String> future = currentThread.thenCompose((data) -> createTime(data));
        System.out.println(future.get());
    }

    private static CompletableFuture<String> createTime(String prefix) {
        return CompletableFuture
                .supplyAsync(() -> prefix + " | " + LocalTime.now().toString());
    }

    /** thenCombine
     *
     */
    @Test
    void then_combine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> currentThreadFuture = CompletableFuture
                .supplyAsync(() -> "Thread: " + Thread.currentThread().getName());

        CompletableFuture<String> currentTimeFuture = CompletableFuture
                .supplyAsync(() -> LocalDateTime.now().toString());

        CompletableFuture<String> future = currentTimeFuture
                .thenCombine(currentThreadFuture,
                        (time, thread) -> time + " " + thread);
        System.out.println(future.get());
    }

    /** allOf
     * 여러 작업을 동시에 실행하고,모든 작업 결과에 콜백을 실행함
     */

    /** anyOf
     *
     */
}
