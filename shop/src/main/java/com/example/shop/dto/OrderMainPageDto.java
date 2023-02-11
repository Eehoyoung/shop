package com.example.shop.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderMainPageDto {
    Page<MainPageOrderDto> mainPageOrderBoards;
    int homeStartPage;
    int homeEndPage;
}
