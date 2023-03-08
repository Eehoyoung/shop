package com.example.shop.dto;

import com.example.shop.model.Mileage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MileagePageDto {
    Page<Mileage> mileageBoards;
    int homeStartPage;
    int homeEndPage;
}
