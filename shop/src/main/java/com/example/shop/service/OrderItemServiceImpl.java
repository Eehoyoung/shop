package com.example.shop.service;

import com.example.shop.exception.ItemNotFoundException;
import com.example.shop.model.OrderItem;
import com.example.shop.model.type.OrderStatus;
import com.example.shop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {


    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem findOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("해당하는 상품이 존재하지 않습니다")
        );
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