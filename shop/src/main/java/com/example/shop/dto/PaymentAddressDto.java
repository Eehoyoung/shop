package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentAddressDto {

    private String address_recipient;
    private String zipcode;
    private String city;
    private String street;
    private String addressHomePhoneNumber;
    private String addressPhoneNumber;
}
