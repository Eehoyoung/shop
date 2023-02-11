package com.example.shop.dto;


import com.example.shop.model.type.UserGrade;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String loginId;
    private UserGrade userGrade;
    private String phoneNumber;
    private int visitCount;
    private int orderCount;
    private LocalDate createdAt;


    @QueryProjection
    public UserDto(Long id, String name, String loginId, UserGrade userGrade, String phoneNumber, int visitCount, int orderCount, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.userGrade = userGrade;
        this.phoneNumber = phoneNumber;
        this.visitCount = visitCount;
        this.orderCount = orderCount;
        this.createdAt = createdAt;
    }
}// Querydsl을 위한 Dto
