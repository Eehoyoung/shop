package com.example.shop.dto;

import com.example.shop.model.type.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private Long orderItemId;
    private String name;
    private String itemName;
    private LocalDate orderedAt;
    private String payment;
    private int orderPrice;
    private OrderStatus orderStatus;

    @QueryProjection
    public OrderDto(Long id, Long orderItemId, String name, String itemName, LocalDate orderedAt, String payment, int orderPrice, OrderStatus orderStatus) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.name = name;
        this.itemName = itemName;
        this.orderedAt = orderedAt;
        this.payment = payment;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;

    }
}
