package com.example.shop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentPriceDto {
    private int total_price;
    private int tobepaid_price;
    private double earnMileage;
    private int used_mileage;
}
