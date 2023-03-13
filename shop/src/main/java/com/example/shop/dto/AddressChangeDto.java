package com.example.shop.dto;

import lombok.*;

@Getter
@Setter
public class AddressChangeDto {
    private Long id;
    private String placeName;
    private String recipient;
    private String city;
    private String streat;
    private String zipcode;
    private String[] addressHomePhoneNumber;
    private String[] addressPhoneNumber;
}
