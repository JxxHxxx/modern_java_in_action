package com.example.modern_java_in_action.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.lang.Math.*;

public class Shop {

    private final String name;

    public Shop(String name) {
        this.name = name;
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    // 비동기 처리
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePrice(product);
            futurePrice.complete(price);
        }).start();

        return futurePrice;
    }

    // 에러 처리 버전
    public Future<Double> getPriceAsyncV2(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                futurePrice.completeExceptionally(e); // 문제 발생하면 발생한 에러를 포함시켜 Future를 종료함
            }
        }).start();

        return futurePrice;
    }

    private double calculatePrice(String product) {
        if (product.equals("ex")) {
            throw new IllegalArgumentException("ex is not valid product");
        }
        delay();
        return random() + product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return this.name;
    }
}
