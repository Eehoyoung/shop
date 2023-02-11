package com.example.shop.dto;


import com.example.shop.model.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String birthday;


    public User toEntity() {
        return User.builder()
                .id(id)
                .loginId(loginId)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .birthday(birthday)
                .build();
    }

}// 회원가입을 위한 Dto