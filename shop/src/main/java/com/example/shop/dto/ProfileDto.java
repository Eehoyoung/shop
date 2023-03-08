package com.example.shop.dto;

import com.example.shop.model.type.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private UserGrade userGrade;
    private String city;
    private String street;
    private String zipcode;
    private String[] homePhoneNumber;
    private String[] phoneNumber;
    private String[] birthday;
    private String email;
}
