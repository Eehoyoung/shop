package com.example.shop.service;

import com.example.shop.dto.ItemDto;
import com.example.shop.model.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findAllCartByUserId(Long id);

    Cart findCartById(Long id);

    void changeCartItemQuantity(Long id, int changeQuantity);

    void deleteCartById(Long id);

    List<ItemDto> CartListToPayment(String itemList);
}
