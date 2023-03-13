package com.example.shop.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class ItemPageDto {

    Page<ItemDto> itemPage;
    int homeStartPage;
    int homeEndPage;
}
