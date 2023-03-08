package com.example.shop.service;

import com.example.shop.dto.AddressChangeDto;
import com.example.shop.dto.AddressDto;
import com.example.shop.model.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService {

    //    주소 등록
    void UserAddres(String loginId, AddressDto addressDto);

    DeliveryAddress findAddressById(Long id);
//    외래키를 사용하여 주소 찾기

    AddressChangeDto addressChange(Long id);

    List<DeliveryAddress> DeliveryAddressByLoginId(String loginId);

    void deleteAddressById(Long id);
//    Pk를 이용해서 주소 삭제

    void updateDeliveryAddress(Long id, AddressChangeDto addressChangeDto);
//    배송 주소 업데이트하는 메소드
}
