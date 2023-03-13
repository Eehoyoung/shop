package com.example.shop.dto;

import lombok.*;

@Getter
@Setter
public class ItemToCartDto {
    private Long id;
    private String itemColor;
    private String itemIdx;
    private String where;
    private String quantity;
}
