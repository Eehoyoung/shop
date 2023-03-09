package com.example.shop.repository;

import com.example.shop.dto.UserDto;
import com.example.shop.model.SearchUser;
import com.example.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface UserRepositoryCustom {
    Page<UserDto> searchAll(Pageable pageable);

    Page<UserDto> searchByCondition(SearchUser search, Pageable pageable);



}
