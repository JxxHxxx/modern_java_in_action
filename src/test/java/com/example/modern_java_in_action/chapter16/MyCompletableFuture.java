package com.example.modern_java_in_action.chapter16;


import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class MyCompletableFuture {
    /**
     * Future : Java5부터 미래의 어느 시점에 결과를 얻는 모델에 활용할 수 있도록 Future 인터페이스를 제공하고 있다.
     * Future의 get() 메서드로 결과를 가져올 수 있다. get 메서드를 호출했을 때 이미 계산이 완료되어 결과가 준비되었다면 즉시 결과를 반환하지만
     * 결과가 준비되지 않았다면 작업이 완료될 땨까지 우리 스레드를 블록시킨다.
     *
     * 아래의 경우 비동기적으로 실행하지만 get을 호출하는 순간 본래 스레드는 blocking 된다.
     * 그래서 line 44가 실행되지 않는 것을 볼 수 있다.
     */
    @Test
    void async_used_by_future() throws InterruptedException {
        /** fixed 와 다르게 새로운 스레드를 더 할당할 수 있다.
         * 60초 동안 사용되지 않은 스레드는 캐시에서 제거된다고 나와있다.
         */
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Long> future = executor.submit(() -> {
            Long result = factorial(11);
            System.out.println("결과 값: " + result);
            return result;
        });

        System.out.println("안녕하세요"); // 비동기 작업을 수행하는 동안 다른 작업을 한다.
        System.out.println("jxx입니다");

        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("작업이 완료되기 전에 인터럽트가 발생했습니다..");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            // Future가 완료되기 전에 타임아웃 발생
            System.out.println("작업이 완료되기 전에 타임아웃이 발생했습니다..");
        }

        System.out.println("여기는 블록되겟죠?");
        Thread.sleep(1000l);
        executor.shutdown();
    }

    public Long factorial(int end) throws InterruptedException {
        Thread.sleep(5000l); // 타임아웃을 위한
        return IntStream.rangeClosed(1, end)
                .asLongStream()
                .reduce(1, Math::multiplyExact);
    }

}

