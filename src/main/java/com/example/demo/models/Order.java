package com.example.demo.models;

import com.example.demo.repositories.ProductRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    @NonNull
    private int userId;
    @NonNull
    private OrderStatus status;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private List<OrderItem> orderItems;

    public static Order convertInputToOrder(String input) {
        String[] orderItemsNotConverted = input.split(",");
        List<OrderItem> orderItems = new LinkedList<>();

        for (String orderItmInfo : orderItemsNotConverted) {
            orderItems.add(OrderItem.convertInputToOrderItem(orderItmInfo));
        }

        return new Order(new Random().nextInt(10000), OrderStatus.PREPARING, LocalDateTime.now(), orderItems);
    }

    public static boolean isInputForOrderMatches(String input) {
        String[] strings = input.split(",");
        for (String str : strings) {
            if (!str.trim().matches("\\d+ \\d+")) {
                return false;
            }
        }
        return true;
    }

    public int calculateTotalPrice() {
        int result = 0;
        for (OrderItem orderItem : orderItems) {
            result += (ProductRepository.getProductById(orderItem.getProductId()).getPrice() * orderItem.getQuantity());
        }
        return result;
    }

    public int calculateTotalItemsQuantity() {
        int result = 0;
        for (OrderItem orderItem : orderItems) {
            result += orderItem.getQuantity();
        }
        return result;
    }

    public void showOrderItems() {
        System.out.println("Product id | Quantity");
        for (int i = 0; i < orderItems.size(); i++) {
            System.out.println((i + 1) + ". " + orderItems.get(i).getProductId() + "       " + orderItems.get(i).getQuantity());
        }
    }

    public void changeQuantity(int indexOfItem, int newQuantity) {
        if (newQuantity == 0) {
            orderItems.remove(indexOfItem - 1);
        } else {
            orderItems.get(indexOfItem - 1).setQuantity(newQuantity);
        }
    }
}
