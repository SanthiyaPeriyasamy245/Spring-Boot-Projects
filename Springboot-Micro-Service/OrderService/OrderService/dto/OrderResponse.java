package com.OrderService.OrderService.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private int id;
    private int product_id;
    private String productName;
    private int quantity;
    private double total_price;
    private double price;


}
