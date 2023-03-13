package com.example.shop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class ItemListToOrderDto {

    private List<Long> itemList;
    private List<Integer> itemCountList;
}
