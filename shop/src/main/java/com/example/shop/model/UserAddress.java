package com.example.shop.model;

import lombok.*;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class UserAddress {
    private String city;
    private String street;
    private String zipcode;

    public UserAddress(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
