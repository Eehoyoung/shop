package com.example.shop.service;

import com.example.shop.dto.*;
import com.example.shop.model.Cart;
import com.example.shop.model.Item;
import com.example.shop.model.SearchItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    Item findItemById(Long id);

    Long saveItem(Item item);
//    상품 저장

    Long ItemSoldOut(String idx, String color);
//    상품 상태 품절로 변경

    Long ItemSale(String idx, String color);
//    상품 상태 판매중으로 변경

    Long deleteItemById(String idx, String color);
//   외래키를 이용해서 아이템 삭제

    ItemDetailDto getItemDetailDto(Long itemIdx);
//    상품 정보 검색

    void putItemInCart(String loginId, Long itemIdx, String itemColor, int quantity);
//    상품을 장바구니에 넣기

    ItemPageDto serchItemPaging(Pageable pageable, String firstCategory, String secondCategory);
//    상품을 페이징하여 조회

    ItemPageDto findAllItemByPaging(Pageable pageable);

    ItemPageDto findAllItemByConditionByPaging(SearchItem searchItem, Pageable pageable);

    List<ItemDto> cartItemDetail(List<Cart> cartList);
//    장바구니에 있는 모든 아이템 조회

    List<ItemDto> Payment(String itemList);


    ItemListToOrderDto Order(String orderItemInfo);
//    주문하기 위한 상품 데이터 준비

    List<Item> MainCarouselItemList();
//    Main Carousel에 상품을 담아 반환하는 메소드

    List<WeeklyBestDto> OuterWeeklyBestItem();
//    Outer 위클리 베스트 상품 반환
    List<WeeklyBestDto> SleeveTopWeeklyBestItem();

    List<WeeklyBestDto> ShirtsWeeklyBestItem();

    List<WeeklyBestDto> BottomWeeklyBestItem();

    List<WeeklyBestDto> ShoesWeeklyBestItem();

    List<WeeklyBestDto> TopKnitWeeklyBestItem();

    List<WeeklyBestDto> NewArrivalItem();

    Page<ItemDto> findAllItem(Pageable pageable);

    Page<ItemDto> findAllItemByCondition(SearchItem searchItem, Pageable pageable);

    Long MaxItemIdx();
//    새로운 상품 등록을 위해 마지막 등록 인덱스 구하기
}
