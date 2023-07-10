package com.example.modern_java_in_action.chapter15;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class MyExecutor {

    /** Java 5 : Executor 프레임워크와 스레드 풀을 통해 스레드의 힘을 높은 수준으로 끌어올리는 기능
     *
     */

    /** 스레드의 문제
     * 자바 스레드는 직접 운영체제 스레드에 접근한다. 운영체제 스레드를 만들고 종료하려면 비싼 비용을 지불해야 한다.
     * 그리고 운영체제 스레드의 숫자는 제한되어 있는 문제도 있다.
     * 운영체제가 지원하는 스레드 수를 초과해 사용하면 자바 애플리케이션이 예상치 못한 방식으로 crash될 수 있다.
     * 그래서 기존 스레드가 실행되는 상태에서 계속 새로운 스레드를 만드는 상황이 일어나지 않도록 주의 해야 한다.
     */

    /** 스레드 풀/스레드 풀이 더 좋은 이유
     * ExecutorService는 테스크를 제출하고 나중에 결과를 수집할 수 있는 인터페이스를 제공한다.
     * 프로그램은 newFixedThreadPool 같은 팩토리 메서드 중 하나를 이용해 스레드 풀을 만들어 사용할 수 있다.
     *
     * 이 메서드는 워커 스레드라 불리는 nThreads를 포함하는 ExecutorService를 만들고 이들을 스레드 풀에 저장한다.
     * 스레드 풀에서 사용하지 않는 스레드로 제출된 테스크를 먼저 온 순서대로 실행한다. 이들 테스크 실행이 종료되면 이들 스레드를 풀로 반환한다.
     * 이 방식의 장점은 하드웨어에 맞는 수의 테스크를 유지함과 동시에 수 천개의 태스크를 스레드 풀에 아무 오버헤드 없이 제출할 수 있다는 점이다.
     * 큐의 크기 조정, 거부 정책, 테스크 종류에 따른 우선순위 등 다양한 설정을 할 수 있다.
     *
     * 프로그래머는 태스크(Runnable or Callable)를 제공하면 스레드가 이를 실행한다.
     */

    /**
     * 스레드 풀/스레드 풀이 나쁜 이유
     * 스레드 풀을 사용하는 것이 스레드를 직접 사용하는 것보다 바람직하지만 두 가지를 주의해야 합니다.
     * <p>
     * (1) k 스레드를 가진 스레드 풀은 오직 k만큼의 스레드를 동시에 실행할 수 있다. 초과로 제출된 태스크는 큐에 저장되며 이전에
     * 태스크 중 하나가 종료되기 전까지는 스레드에 할당하지 않는다. 불필요하게 많은 스레드를 만드는 일을 피할 수 있으므로 보통
     * 이 상황은 아주 문제가 되지 않지만 잠을 자거나 I/O를 기다리거나 네트워크 연결을 기다리는 태스크가 있다면 주의해야 한다.
     * I/O를 기다리는 블록 상황에서 이들 태스크가 워크 스레드에 할당된 상태를 유지하지만 아무 작업도 하지 않게 된다.
     * <p>
     * 처음 제출한 태스크가 기존 실행중인 태스크가 나중의 태스크 제출을 기다리는 상황이라면 데드락이 걸릴 수도 있다.
     * 핵심은 블록할 수 있는 태스크는 스레드 풀에 제출하지 말아야한다는 것이지만 항상 이를 지킬수는 업삳.
     * <p>
     * (2) 중요한 코드를 실행하는 스레드가 죽는 일이 발생하지 않도록 보통 자바 프로그램은 main이 반환하기 전에 모든 스레드의
     * 작업이 끝나길 기다린다. 따라서 프로그램을 종료하기 전에 모든 스레드 풀을 종료하는 습관을 갖는 것이 중요하다.
     * cuz 풀의 워커 스레드가 만들어진 다음 다른 태스크 제출을 기다리면서 종료되지 않은 상태일 수 있으므로
     */

    @Test
    void executor2() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> System.out.println("new Thread : " + Thread.currentThread().getName()));

        // graceful shutdown
        executorService.shutdown();

        // hard shutdown
//        executorService.shutdownNow();
    }

    @Test
    void executor3() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(getRunnable("hello"));
        executorService.execute(getRunnable("world"));
        executorService.execute(getRunnable("hello"));
        executorService.execute(getRunnable("I'm"));
        executorService.execute(getRunnable("Jxx"));

        executorService.shutdown();
    }

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + " : " + Thread.currentThread().getName());
    }

    @Test
    void executor4() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.schedule(getRunnable("hello"), 3, TimeUnit.SECONDS);

        Thread.sleep(5000L);
        scheduledExecutorService.shutdown();
    }

    @Test
    void callable_void() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                System.out.println("Thread: " + Thread.currentThread().getName());
                return null;
            }
        };

        executorService.submit(callable);
        executorService.shutdown();
    }

    @Test
    void callable_String() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                return "Thread: " + Thread.currentThread().getName();
            }
        };

        executorService.submit(callable);
        executorService.shutdown();
    }

    @Test
    void future() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            Thread.sleep(3000L);
            return "Thread: " + Thread.currentThread().getName() + " 작업 시작";
        };


        Future<String> future = executorService.submit(callable);
        System.out.println(future.get()); // get() 블로킹 방식으로 결과를 가져옴
        // 스레드 풀 X
        new Thread(() -> System.out.println("Thread: " + Thread.currentThread().getName() + " 작업 시작")).start();
        executorService.shutdown();
    }
}
