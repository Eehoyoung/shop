package com.example.shop.dto;

import com.example.shop.model.Mileage;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class MileagePageDto {
    Page<Mileage> mileageBoards;
    int homeStartPage;
    int homeEndPage;
}
