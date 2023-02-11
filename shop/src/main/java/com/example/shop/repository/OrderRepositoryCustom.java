package com.example.shop.repository;

import com.example.shop.dto.MainPageOrderDto;
import com.example.shop.dto.OrderDto;
import com.example.shop.model.SearchOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<OrderDto> searchAllOrder(Pageable pageable);

    Page<OrderDto> searchAllOrderByCondition(SearchOrder searchOrder, Pageable pageable);

    Page<MainPageOrderDto> mainPageSearchAllOrder(Pageable pageable, String loginId);

    Page<MainPageOrderDto> mainPageSearchAllOrderByCondition(SearchOrder searchOrder, Pageable pageable, String loginId);
}
