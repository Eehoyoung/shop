package com.example.shop.dto;

import com.example.shop.model.type.OrderStatus;
import lombok.*;

@Getter
@Setter
public class OrderInfo {
    private String name;

    private OrderStatus orderStatus;

    private String itemName;

    private int orderPrice;
}
