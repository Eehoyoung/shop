package com.example.shop.repository;

import com.example.shop.dto.UserDto;
import com.example.shop.model.SearchUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserRepositoryCustom {
    Page<UserDto> searchAll(Pageable pageable);

    Page<UserDto> searchByCondition(SearchUser search, Pageable pageable);


}
