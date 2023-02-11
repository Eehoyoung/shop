package com.example.shop.service;

import com.example.shop.dto.MileagePageDto;
import com.example.shop.model.Mileage;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MileageService {

    int totalMileage(String loginId);

    int usedMileage(String loginId);

    int availableMileage(int totalMileage, int usedMileage);

    List<Mileage> MileageDetail(String loginId);

    Long joinMileage(Long id);

    MileagePageDto mileagePaging(String loginId, Pageable pageable);
}
