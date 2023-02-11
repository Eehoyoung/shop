package com.example.shop.service;

import com.example.shop.model.OrderItem;
import com.example.shop.model.type.OrderStatus;

public interface OrderItemService {

    OrderItem findOrderItemById(Long id);

    void chagneOrderStatus(Long id, OrderStatus orderStatus);
}
