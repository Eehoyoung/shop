package com.example.shop.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class OrderMainPageDto {
    Page<MainPageOrderDto> mainPageOrderBoards;
    int homeStartPage;
    int homeEndPage;
}
