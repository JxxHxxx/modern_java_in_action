package com.example.modern_java_in_action.chapter15;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;


public class MyCallable {


    /** Runnable VS Callable */

    @Test
    void runnable() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                Thread.sleep(3000L);
                System.out.println("Thread: " + Thread.currentThread().getName() + " 작업 수행");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Thread: " + Thread.currentThread().getName() + " 작업 수행");
        // Test worker 스레드가 작업을 종료하면서 싱글풀에 있는 스레드의 태스크는 수행되지 않는다.
    }

    /** future는 미래에 완료된 Callable의 반환값을 구하기 위해 사용할 수 있다. */
    @Test
    void callable_and_future() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            Thread.sleep(3000L);
            String result = "Thread: " + Thread.currentThread().getName() + " 작업 수행";
            System.out.println(result);
            return result; // callable은 runnable과 다르게 return 값을 가질 수 있다.
        };
        // runnable 대신 callable 을 인자로 받는다.
        Future<String> future = executorService.submit(callable);
        future.get(); // 블락킹함

        Thread thread = new Thread(() ->
                System.out.println("Thread: " + Thread.currentThread().getName() + " 작업 수행"));
        thread.start();
        executorService.shutdown();
    }

    @Test
    void future_isDone_isCancel() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(3500L);
            String result = "Thread: " + Thread.currentThread().getName();
            System.out.println(result);
            return result;
        };

        Future<String> future = executorService.submit(callable);
        System.out.println("future.isDone() " + future.isDone());
        System.out.println("future.isCancelled()() " + future.isCancelled());
        future.get();
        System.out.println("future.isDone() " + future.isDone());
        System.out.println("future.isCancelled() " + future.isCancelled());
    }

    @Test
    void future_cancel_1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(3500L);
            String result = "Thread: " + Thread.currentThread().getName();
            System.out.println(result);
            return result;
        };

        Future<String> future = executorService.submit(callable);
        // @Param mayInterruptIfRunning = 현재 작업중인 스레드에 인터럽트를 발생시킬지 여부
        // true 이면 인터럽트를 발생시키고 즉시 종료 false 는 현재 작업을 마무리함
        // 개인적인 생각이지만 블락킹 환경에서는 cancel을 효과적으로 사용하기가 애매해보인다.
        future.cancel(true);

        assertThat(future.isDone()).isTrue();
        assertThat(future.isCancelled()).isTrue();
        // 작업이 취소될 경우 get() 호출 시 취소 예외가 발생한다.
        assertThatThrownBy(() -> future.get())
                .isInstanceOf(CancellationException.class);
    }

    @Test
    void multi_thread_pool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Runnable runnable = () -> System.out.println("Thread: " + Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            executorService.execute(runnable);
        }
        //when
        executorService.shutdown();
        //then : shutdown이 일어난 뒤에 실행하려고 할 경우 RejectedExecutionException 이 발생한다.
        assertThatThrownBy(() -> executorService.execute(runnable))
                .isInstanceOf(RejectedExecutionException.class);
    }
}
