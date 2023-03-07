package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemPageDto {

    Page<ItemDto> itemPage;
    int homeStartPage;
    int homeEndPage;
}
