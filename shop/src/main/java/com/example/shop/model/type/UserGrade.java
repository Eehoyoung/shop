package com.example.shop.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserGrade {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;
}
