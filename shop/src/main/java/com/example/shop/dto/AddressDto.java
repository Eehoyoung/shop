package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto {

    private Long id;
    private String placeName;
    private String recipient;
    private String city;
    private String streat;
    private String zipcode;
    private String addressHomePhoneNumber;
    private String addressPhoneNumber;
}
