package com.example.modern_java_in_action;


public class Order {
    private final String ordererName;
    private final String dishName;

    public Order(String ordererName, String dishName) {
        this.ordererName = ordererName;
        this.dishName = dishName;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public String getDishName() {
        return dishName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ordererName='" + ordererName + '\'' +
                ", dishName='" + dishName + '\'' +
                '}';
    }
}
