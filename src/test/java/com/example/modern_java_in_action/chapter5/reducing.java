package com.example.modern_java_in_action.chapter5;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

/** 스트림 요소를 조합해서 복잡한 질의를 표현할 때 유용하다.
 *
 */
public class reducing {

    /** reduce()
     * @Param identity = 초기 값을 의미한다.
     */
    @Test
    void reduce_basic() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        // 메소드 참조를 사용
        Integer sum= numbers.stream().reduce(0, Integer::sum);
        // 함수형 인터페이스 구현 -> 람다로 변경
        Integer sum1 = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println("total : " + sum1);
    }

    @Test
    void reduce_no_identity() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        Optional<Integer> sum = numbers.stream().reduce(Integer::sum);

    }

    @Test
    void get_min() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        Optional<Integer> min = numbers.stream().reduce((x, y) -> x < y ? x : y);
        Optional<Integer> min2 = numbers.stream().reduce(Integer::min);
    }

    @Test
    void intStream() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        int sum = numbers.stream()
                .mapToInt(x -> x.intValue())
                .sum();

        System.out.println("합계 : " + sum);
    }

}
