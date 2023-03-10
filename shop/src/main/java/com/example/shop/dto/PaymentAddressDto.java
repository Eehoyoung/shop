package com.example.shop.dto;

import lombok.*;

@Getter
@Setter
public class PaymentAddressDto {

    private String address_recipient;
    private String zipcode;
    private String city;
    private String street;
    private String addressHomePhoneNumber;
    private String addressPhoneNumber;
}
