package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderMainPageDto {
    Page<MainPageOrderDto> mainPageOrderBoards;
    int homeStartPage;
    int homeEndPage;
}
