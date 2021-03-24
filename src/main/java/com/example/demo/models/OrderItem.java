package com.example.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class OrderItem {
    private int orderId;
    @NonNull
    private int productId;
    @NonNull
    private int quantity;

    public static OrderItem convertInputToOrderItem(String input) {
        String[] orderInformation = input.trim().split(" ");
        return new OrderItem(Integer.parseInt(orderInformation[0]), Integer.parseInt(orderInformation[1]));
    }
}
