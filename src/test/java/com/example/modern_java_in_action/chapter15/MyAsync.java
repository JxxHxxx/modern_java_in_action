package com.example.modern_java_in_action.chapter15;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class MyAsync {

    /**
     * 실행할 작업들을 추가하고, 작업의 상태와 결과를 포함하는 Future 반환
     * Future get() 호출하면 성공적으로 작업이 완료된 후 결과를 얻을 수 있다.
     */
    @Test
    void submit() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<String> submit = executorService.submit(createCallable(0));
        submit.get();
    }

    /**
     * 모든 결과가 나올 때 까지 대기하는 블로킹 방식의 요청
     * 동시에 주어진 작업을 모두 실행하고, 전부 끝나면 각각의 상태와 결과를 갖는 List<Future> 반환
     */
    @Test
    void invoke_all() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Callable<String> callable1 = createCallable(0);
        Callable<String> callable2 = createCallable(1000);
        Callable<String> callable3 = createCallable(2000);

        List<Future<String>> futures = executorService.invokeAll(List.of(callable1, callable2, callable3));
        futures.forEach(f -> {
            try {
                System.out.println(f.get() + " 작업 완료 여부" + f.isDone());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 가장 빨리 실행된 결과가 나올 때 까지 대기하는 블로킹 방식의 요청
     */
    @Test
    void invoke_any() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable<String> callable1 = createCallable("1",0);
        Callable<String> callable2 = createCallable("2",1000);
        Callable<String> callable3 = createCallable("3",2000);

        String result = executorService.invokeAny(Set.of(callable1, callable2, callable3));
        System.out.println(result);
    }

    private static Callable<String> createCallable(int sleepMillis) {
        Callable<String> callable1 = () -> {
            Thread.sleep(sleepMillis);
            String result = Thread.currentThread().getName();
            System.out.println("작업 실행:" + result);
            return result;
        };
        return callable1;
    }

    private static Callable<String> createCallable(String prefix, int sleepMillis) {
        Callable<String> callable1 = () -> {
            Thread.sleep(sleepMillis);
            String result = Thread.currentThread().getName();
            System.out.println("작업 실행:" + result);
            return prefix + ": " + result;
        };
        return callable1;
    }

}
