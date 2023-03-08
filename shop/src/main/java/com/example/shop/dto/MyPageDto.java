package com.example.shop.dto;

import com.example.shop.model.type.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyPageDto {
    private String name;
    private UserGrade grade;
    private int mileage;
}
