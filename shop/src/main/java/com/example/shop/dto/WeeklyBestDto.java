package com.example.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeeklyBestDto {
    private Long itemIdx;
    private String itemName;
    private int price;
    private String itemInfo;
    private String imgUrl;
    private int mileage;

    @QueryProjection
    public WeeklyBestDto(Long itemIdx, String itemName, int price, String itemInfo, String imgUrl) {
        this.itemIdx = itemIdx;
        this.itemName = itemName;
        this.price = price;
        this.itemInfo = itemInfo;
        this.imgUrl = imgUrl;
    }

    @QueryProjection
    public WeeklyBestDto(Long itemIdx, String itemName, int price, String imgUrl) {
        this.itemIdx = itemIdx;
        this.itemName = itemName;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
