package com.example.modern_java_in_action.chapter5;

import com.example.modern_java_in_action.Dish;
import com.example.modern_java_in_action.Order;
import com.example.modern_java_in_action.Type;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

public class Flatmap {

    @Test
    void predicate() {
        List<Dish> menus = List.of(new Dish("seasonal fruit", true, 120, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 350, Type.OTHER));

        List<String> vegetarians = List.of("jxx", "hxxx", "xuni");

        List<Order> orders = vegetarians.stream()
                .flatMap(vegetarian -> menus.stream()
                        .filter(Dish::isVegetarian)
                        .map(dish -> new Order(vegetarian, dish.getName()))
                )
                .toList();

        orders.forEach(order -> System.out.println(order.toString()));

    }

    private static Stream<Order> createVegetarianOrder(List<Dish> menus, String vegetarian) {
        return menus.stream()
                .filter(Dish::isVegetarian)
                .map(dish -> new Order(vegetarian, dish.getName()));
    }
}
