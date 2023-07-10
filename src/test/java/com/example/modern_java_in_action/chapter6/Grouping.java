package com.example.modern_java_in_action.chapter6;

import com.example.modern_java_in_action.Dish;
import com.example.modern_java_in_action.Order;
import com.example.modern_java_in_action.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class Grouping {

    List<Dish> menus;

    @BeforeEach
    void beforeEach() {
        menus = List.of(
                new Dish("seasonal fruit", true, 120, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 270, Type.OTHER));
    }

    @Test
    void short_circuit() {
        menus.stream()
                .filter(Dish::isVegetarian)
                .filter(dish -> dish.getKcal() < 300)
                .toList();
    }

    @Test
    void blog() {
        List<String> orderers = List.of("jxx","kxx","lxx");

        List<Order> orders = orderers.stream()
                .flatMap(orderer -> menus.stream()
                        .filter(dish -> dish.isVegetarian())
                        .filter(dish -> dish.getKcal() < 300)
                        .map(dish -> new Order(orderer, dish.getName()))
                ).toList();

    }

    @Test
    void group() {
        Map<Type, List<Dish>> dishesByType = menus.stream()
                .collect(groupingBy(Dish::getType));

        dishesByType.forEach((t, d) -> System.out.println(t + " " + d.toString()));

    }

    @Test
    void group_2() {
        Map<Type, List<String>> collect = menus.stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
    }

    @Test
    void group_collecting_and_then() {
        menus.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getKcal)),
                                c -> c.get()
                        )));
    }

    /**
     * 분할  : predicate 분류 함수를 사용해 그룹화 한다.
     * 장점 : 분할한 요소의 스트림 리스트를 모두 유지할 수 있다는 것이 장점
     */
    @Test
    void partitioning() {
        Map<Boolean, List<Dish>> partitionMenu = menus.stream().collect(
                partitioningBy(Dish::isVegetarian));

        partitionMenu.forEach(
                (k, v) -> System.out.println("is Vegetarian Food? " + k + " " + v.toString())
        );
    }

    @Test
    void partitioning_V2() {
        Map<Boolean, Map<Boolean, List<Dish>>> partitionMenu = menus.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        partitioningBy(dish -> dish.getKcal() > 500)
                ));

        partitionMenu.get(true).forEach((k, v) -> System.out.println("kcal is over 500 ? " + k + " " + v));
    }

    /** practice Collector */
    @Test
    void collector() {

    }
}
