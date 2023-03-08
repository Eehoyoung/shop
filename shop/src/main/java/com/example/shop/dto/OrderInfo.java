package com.example.shop.dto;

import com.example.shop.model.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderInfo {
    private String name;

    private OrderStatus orderStatus;

    private String itemName;

    private int orderPrice;
}
