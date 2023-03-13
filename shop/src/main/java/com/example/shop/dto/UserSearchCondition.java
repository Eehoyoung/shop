package com.example.shop.dto;

import lombok.*;

@Getter
@Setter
public class UserSearchCondition {
    private String name;
    private String loginId;

    public UserSearchCondition(String name, String loginId) {
        this.name = name;
        this.loginId = loginId;
    }

}
