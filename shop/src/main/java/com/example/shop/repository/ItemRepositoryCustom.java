package com.example.shop.repository;

import com.example.shop.dto.ItemDto;
import com.example.shop.dto.WeeklyBestDto;
import com.example.shop.model.SearchItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Page<ItemDto> searchAllItem(Pageable pageable);

    Page<ItemDto> searchAllItemByCondition(SearchItem searchItem, Pageable pageable);

    Page<ItemDto> findAllItem(Pageable pageable, String firstCategory, String secondCategory);

    Long searchMaxItemIdx();

    ItemDto findAllItemInBasket(Long itemId);

    List<WeeklyBestDto> findWeeklyBestItem(String firstCategory, String secondCategory, boolean rep);

    List<WeeklyBestDto> findNewArrivalItem(String firstCategory, String secondCategory, boolean rep);

}
