package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDetailDto {

    private Long itemIdx;
    private String imgMainUrl;
    private String itemName;
    private int price;
    private double mileage;
    private String itemInfo;
    private List<String> colorList;
    private String fabric;
    private String model;
    private List<Long> itemId;
    private List<String> imgUrlList;
}
