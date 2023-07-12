package com.example.compelteableapi;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Callback {

    /**
     * 반환 값을 받아서 다른 값을 반환함
     * 함수형 인터페이스 Function을 파라미터로 받음
     */
    @Test
    void then_apply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "Thread: " + Thread.currentThread().getName())
                .thenApply(data -> "Time : " + LocalTime.now() + " | " + data)
                .thenApply(data -> "[ID : " + UUID.randomUUID() +" " + data + "]");

        System.out.println(future.get());
    }


    /**
     * 반환 값을 받아 처리하고 값을 반환하지 않음
     * void type 으로 변경됨
     */
    @Test
    void then_accept() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> "Thread: " + Thread.currentThread().getName())
                .thenAccept(s -> System.out.println(s));

        future.get();
    }

    /**
     * 반환 값을 받지 않고 다른 작업을 실행함
     * 받지는 않지만 동일한 스레드에서 실행되는 것 같음
     */
    @Test
    void then_run() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    return "Thread: " + Thread.currentThread().getName();
                })
                .thenRun(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        });

        future.get();
    }

}
