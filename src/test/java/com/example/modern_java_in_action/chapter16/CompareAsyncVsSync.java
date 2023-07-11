package com.example.modern_java_in_action.chapter16;

import com.example.modern_java_in_action.completablefuture.Shop;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompareAsyncVsSync {

    /** 비동기 처리를 할 경우
     * getPriceXxx 에서 결과 갑슬 얻기 위해 본 스레드가 블락되지 않음
     */

    @Test
    void async() throws InterruptedException {
        System.out.println("start");
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product"); // 상점에 제품가격 정보 요청
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        //제품의 가격을 계산하는 동안
        System.out.println("다른 처리를 합니다...");
        //다른 상점 검색 등 다른 작업 수행
        try {
            Double price = futurePrice.get();
            System.out.printf("Price is %.2f%n" , price);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = ((System.nanoTime() - start)/ 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");

        Thread.sleep(5000L);
    }

    /** 동기 처리를 할 경우
     * getPrice 에서 블락킹이됨 아래 메서드를 실행하면 약 1초 정도 블락킹이 되는 것을 볼 수 있음
     */

    @Test
    void sync() throws InterruptedException {
        System.out.println("start");
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        double price = shop.getPrice("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        System.out.printf("Price is %.2f%n" , price);
        //제품의 가격을 계산하는 동안
        System.out.println("다른 처리를 합니다...");
        //다른 상점 검색 등 다른 작업 수행

        long retrievalTime = ((System.nanoTime() - start)/ 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");

        Thread.sleep(5000L);
    }

    // 예외를 처리하지 않을 경우 발생하는 문제 -> 예외가 발생한 스레드에만 영향을 미침 / 본 스레드는 예외가 발생했는지 모르고 계속 결과 값을 기다림

    @Test
    void async_handle_exc() throws InterruptedException {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("ex"); // 예외가 발생하지만 계속 가격을 구하려고 한다.

        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        // 이 쓰레드에서 예외가 발생하는데
        System.out.println("다른 처리를 합니다...");
        // 아래 로직을 계속 수행하려고 한다.

        try {
            Double price = futurePrice.get(5, TimeUnit.SECONDS);
            System.out.printf("Price is %.2f%n" , price);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.out.println("예외를 차리하지 못해 타임 아웃이 발생하였습니다.");
        }

        long retrievalTime = ((System.nanoTime() - start)/ 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");

        Thread.sleep(2000L);
    }
}
