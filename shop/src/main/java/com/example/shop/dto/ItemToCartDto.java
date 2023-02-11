package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemToCartDto {
    private Long id;
    private String itemColor;
    private String itemIdx;
    private String where;
    private String quantity;
}
