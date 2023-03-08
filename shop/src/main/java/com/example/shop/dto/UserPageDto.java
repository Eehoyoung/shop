package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPageDto {
    Page<UserDto> userPage;
    int StartPage;
    int EndPage;
}
