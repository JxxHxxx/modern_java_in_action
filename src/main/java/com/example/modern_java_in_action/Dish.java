package com.example.modern_java_in_action;

public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int kcal;
    private final Type type;

    public Dish(String name, boolean vegetarian, int kcal, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.kcal = kcal;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getKcal() {
        return kcal;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                '}';
    }
}
