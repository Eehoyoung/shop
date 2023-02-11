package com.example.shop.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPageDto {
    Page<OrderDto> orderBoards;
    int homeStartPage;
    int homeEndPage;
}
