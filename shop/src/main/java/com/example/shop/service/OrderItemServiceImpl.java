package com.example.shop.service;

import com.example.shop.exception.ItemNotFoundException;
import com.example.shop.model.OrderItem;
import com.example.shop.model.type.OrderStatus;
import com.example.shop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public OrderItem findOrderItemById(Long id) {
        OrderItem findOrderItem = orderItemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("해당하는 상품이 존재하지 않습니다")
        );
        return findOrderItem;
    }

    @Override
    @Transactional
    public void chagneOrderStatus(Long id, OrderStatus orderStatus) {
        OrderItem findOrderItem = orderItemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("해당하는 상품이 존재하지 않습니다")
        );
        findOrderItem.setOrderStatus(orderStatus);
    }
}