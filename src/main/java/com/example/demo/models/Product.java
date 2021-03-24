package com.example.demo.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private int price;
    private ProductStatus status;
    private LocalDateTime createdAt;

    public Product(String name, int price, ProductStatus status, LocalDateTime createdAt) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Product createProductFromInput(String input) {
        String[] strings = input.split(" ");
        return new Product(strings[0], Integer.parseInt(strings[1]), ProductStatus.values()[Integer.parseInt(strings[2])-1], LocalDateTime.now());
    }
}
