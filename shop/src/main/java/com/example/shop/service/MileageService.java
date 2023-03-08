package com.example.shop.service;

import com.example.shop.dto.MileagePageDto;
import com.example.shop.model.Mileage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MileageService {

    int totalMileage(String loginId);
    //전체 마일리지 조회

    int usedMileage(String loginId);

    //    사용한 마일리지 조회
    int availableMileage(int totalMileage, int usedMileage);

    //    사용 가능한 마일리지 조회
    List<Mileage> MileageDetail(String loginId);
//  해당 회원의 마일리지 상세 조회

    Long joinMileage(Long id);
//  회원가입 축하 마일리지

    MileagePageDto mileagePaging(String loginId, Pageable pageable);
}
