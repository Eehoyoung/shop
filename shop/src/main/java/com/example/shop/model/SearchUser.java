package com.example.shop.model;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchUser {
    //타임리프의 name이 이 부분과 같아야 한다.
    private String searchCondition;
    private String searchKeyword;
}
