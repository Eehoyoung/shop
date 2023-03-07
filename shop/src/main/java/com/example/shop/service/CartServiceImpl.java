package com.example.shop.service;

import com.example.shop.dto.ItemDto;
import com.example.shop.exception.CartNotFoundException;
import com.example.shop.model.Cart;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ItemRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<Cart> findAllCartByUserId(Long id) {
        return cartRepository.findAllByUserId(id);
    }

    @Override
    public Cart findCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new CartNotFoundException("해당하는 장바구니를 찾을 수 없습니다")
        );
    }

    @Transactional
    @Override
    public void changeCartItemQuantity(Long id, int changeQuantity) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new CartNotFoundException("해당하는 장바구니를 찾을 수 없습니다.")
        );
        cart.setCartCount(changeQuantity);
    }

    @Override
    @Transactional
    public void deleteCartById(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public List<ItemDto> CartListToPayment(String itemList) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        JsonArray jsonArray = (JsonArray) JsonParser.parseString(itemList);

        List<Long> cartIdList = new ArrayList<>();
        List<Integer> cartQuantityList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = (JsonObject) jsonArray.get(i);
            String id = object.get("id").getAsString();
            String quantity = object.get("quantity").getAsString();

            cartIdList.add(Long.parseLong(id));
            cartQuantityList.add(Integer.parseInt(quantity));
        }

        for (Long aLong : cartIdList) {
            Optional<Cart> byId = cartRepository.findById(aLong);
            Cart cart = byId.orElseThrow(
                    () -> new CartNotFoundException("해당하는 장바구니가 존재하지 않습니다.")
            );
            Long itemId = cart.getItem().getId();

            itemDtoList.add(itemRepository.findAllItemInCart(itemId));
        }

        return itemDtoList;
    }
}
