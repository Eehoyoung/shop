package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MileageDto {
    private Long id;
    private int mileagePrice;
    private String mileageContent;

}
