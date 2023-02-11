package com.example.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CartDto {

    private Long cartId;
    private int cartCount;
    private Long itemId;
    private String itemName;
    private String color;
    private String imgUrl;
    private int price;

    @QueryProjection
    public CartDto(Long basketId, int basketCount, Long itemId, String itemName, String color, String imgUrl, int price) {
        this.cartId = basketId;
        this.cartCount = basketCount;
        this.itemId = itemId;
        this.itemName = itemName;
        this.color = color;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
