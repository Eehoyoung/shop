package com.example.shop.dto;

import com.example.shop.model.type.OrderStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderInfo {
    private String name;

    private OrderStatus orderStatus;

    private String itemName;

    private int orderPrice;
}
