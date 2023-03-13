package com.example.shop.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class UserPageDto {
    Page<UserDto> userPage;
    int StartPage;
    int EndPage;
}
