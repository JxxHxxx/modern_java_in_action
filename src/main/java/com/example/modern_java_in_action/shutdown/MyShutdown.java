package com.example.modern_java_in_action.shutdown;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyShutdown {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Runnable runnable = () -> System.out.println("Thread: " + Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            executorService.execute(runnable);
        }

        Thread thread = Thread.currentThread();
        while (thread.isAlive()){
            Thread.sleep(1000L);
            System.out.println("현재 스레드 " + Thread.currentThread().getName() + "이(가) 실행중입니다.");
        }
    }
}
