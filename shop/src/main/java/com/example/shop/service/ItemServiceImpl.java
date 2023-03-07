package com.example.shop.service;

import com.example.shop.dto.*;
import com.example.shop.exception.unSaveIdException;
import com.example.shop.model.Cart;
import com.example.shop.model.Item;
import com.example.shop.model.SearchItem;
import com.example.shop.model.User;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new unSaveIdException("존재 하지않는 상품입니다."));
    }

    @Transactional
    @Override
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional
    @Override
    public Long ItemSoldOut(String idx, String color) {
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx, color);
        for (Item changeItem : findItem) {
            changeItem.setSaleStatus("soldout");
        }
        return itemIdx;
    }

    @Transactional
    @Override
    public Long ItemSale(String idx, String color) {
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx, color);
        for (Item changeItem : findItem) {
            changeItem.setSaleStatus("onsale");
        }
        return itemIdx;
    }

    @Transactional
    @Override
    public Long deleteItemById(String idx, String color) {
        Long itemIdx = Long.parseLong(idx);
        List<Item> findItem = itemRepository.findAllByItemIdxAndColor(itemIdx, color);
        for (Item changeItem : findItem) {
            itemRepository.deleteById(changeItem.getId());
        }
        return itemIdx;
    }

    @Override
    public ItemDetailDto getItemDetailDto(Long itemIdx) {

        List<Item> itemListByItemIdx = itemRepository.findAllByItemIdx(itemIdx);

        List<Item> findItemByitemIdxAndRep = itemRepository.findAllByItemIdxAndRep(itemIdx, true);
        String imgMainUrl = findItemByitemIdxAndRep.get(0).getImgUrl();
        List<String> getColorList = new ArrayList<>();
        for (Item item : findItemByitemIdxAndRep) {
            getColorList.add(item.getColor());
        }

        Item topItemByItemIdxAndRep = itemRepository.findTopByItemIdxAndRep(itemIdx, true);
        String itemName = topItemByItemIdxAndRep.getItemName();
        int price = topItemByItemIdxAndRep.getPrice();
        String itemInfo = topItemByItemIdxAndRep.getItemInfo();
        String itemFabric = topItemByItemIdxAndRep.getFavric();
        String itemModel = topItemByItemIdxAndRep.getModel();
        double mileage = topItemByItemIdxAndRep.getPrice() * 0.01;

        List<Long> idList = new ArrayList<>();
        for (Item listByItemIdx : itemListByItemIdx) {
            idList.add(listByItemIdx.getId());
        }

        List<String> imgUrlList = new ArrayList<>();
        List<Item> itemByItemIdxAndColor = itemRepository.findAllByItemIdxAndColor(itemIdx, topItemByItemIdxAndRep.getColor());

        for (Item item : itemByItemIdxAndColor) {
            imgUrlList.add(item.getImgUrl());
        }

        ItemDetailDto itemDetailDto = new ItemDetailDto();
        itemDetailDto.setImgMainUrl(imgMainUrl); //대표사진
        itemDetailDto.setColorList(getColorList); //아이템 색깔리스트
        itemDetailDto.setItemName(itemName); //이름
        itemDetailDto.setPrice(price); //가격
        itemDetailDto.setItemInfo(itemInfo); //정보
        itemDetailDto.setFabric(itemFabric);//원단
        itemDetailDto.setModel(itemModel); //모델
        itemDetailDto.setItemIdx(itemIdx); //번호
        itemDetailDto.setItemId(idList); //고유번호
        itemDetailDto.setMileage(mileage); //마일리지
        itemDetailDto.setImgUrlList(imgUrlList); //사진리스트

        return itemDetailDto;
    }

    @Override
    public void putItemInCart(String loginId, Long itemIdx, String itemColor, int quantity) {
        Cart cart = new Cart();
        User findUser = userRepository.findByloginId(loginId).get();
        Item findItem = itemRepository.findByItemIdxAndColorAndRep(itemIdx, itemColor, true);
        cart.setUser(findUser);
        cart.setItem(findItem);
        cart.setCartCount(quantity);

        cartRepository.save(cart);
    }

    @Override
    public ItemPageDto serchItemPaging(Pageable pageable, String firstCategory, String secondCategory) {
        ItemPageDto itemPageDto = new ItemPageDto();

        Page<ItemDto> itemBoards = itemRepository.findAllItem(pageable, firstCategory, secondCategory);
//        int homeStartPage = Math.max(1, itemBoards.getPageable().getPageNumber() - 1);
        int homeStartPage = 1;
//        int homeEndPage = Math.min(itemBoards.getTotalPages(), itemBoards.getPageable().getPageNumber() + 3);
        int homeEndPage = 2;

        itemPageDto.setItemPage(itemBoards);
        itemPageDto.setHomeStartPage(homeStartPage);
        itemPageDto.setHomeEndPage(homeEndPage);

        return itemPageDto;
    }

    @Override
    public ItemPageDto findAllItemByPaging(Pageable pageable) {
        ItemPageDto itemPageDto = new ItemPageDto();
        Page<ItemDto> itemBoards = itemRepository.searchAllItem(pageable);

        return getItemPageDto(itemPageDto, itemBoards);
    }

    @NotNull
    private ItemPageDto getItemPageDto(ItemPageDto itemPageDto, Page<ItemDto> itemBoards) {
        int homeStartPage = Math.max(1, itemBoards.getPageable().getPageNumber() - 1);
        int homeEndPage = Math.min(itemBoards.getTotalPages(), itemBoards.getPageable().getPageNumber() + 3);

        itemPageDto.setItemPage(itemBoards);
        itemPageDto.setHomeStartPage(homeStartPage);
        itemPageDto.setHomeEndPage(homeEndPage);

        return itemPageDto;
    }

    @Override
    public ItemPageDto findAllItemByConditionByPaging(SearchItem searchItem, Pageable pageable) {
        ItemPageDto itemPageDto = new ItemPageDto();
        Page<ItemDto> itemBoards = itemRepository.searchAllItemByCondition(searchItem, pageable);

        return getItemPageDto(itemPageDto, itemBoards);
    }

    @Override
    public List<ItemDto> cartItemDetail(List<Cart> cartList) {
        List<ItemDto> cartItemDetail = new ArrayList<>();

        for (Cart cart : cartList) {
            Long itemId = cart.getItem().getId();
            cartItemDetail.add(itemRepository.findAllItemInCart(itemId));
        }
        return cartItemDetail;
    }

    @Override
    public List<ItemDto> Payment(String itemList) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        ItemDto itemDto = new ItemDto();

        JsonArray jsonArray = (JsonArray) JsonParser.parseString(itemList);
        JsonObject object = (JsonObject) jsonArray.get(0);

        String id = object.get("idx").getAsString();
        String color = object.get("color").getAsString();
        String quantity = object.get("quantity").getAsString();

        Long itemIdx = Long.parseLong(id);
        int orderCount = Integer.parseInt(quantity);

        Item byItemIdxAndColorAndRep = itemRepository.findByItemIdxAndColorAndRep(itemIdx, color, true);
        itemDto.setItemIdx(byItemIdxAndColorAndRep.getItemIdx());
        itemDto.setItemName(byItemIdxAndColorAndRep.getItemName());
        itemDto.setColor(byItemIdxAndColorAndRep.getColor());
        itemDto.setCartConunt(orderCount);
        itemDto.setId(byItemIdxAndColorAndRep.getId());
        itemDto.setPrice(byItemIdxAndColorAndRep.getPrice());
        itemDto.setImgUrl(byItemIdxAndColorAndRep.getImgUrl());

        itemDtoList.add(itemDto);

        return itemDtoList;
    }

    @Override
    public ItemListToOrderDto Order(String orderItemInfo) {
        ItemListToOrderDto itemListToOrderDto = new ItemListToOrderDto();

        JsonArray jsonArray = (JsonArray) JsonParser.parseString(orderItemInfo);

        List<Long> itemList = new ArrayList<>();
        List<Integer> itemCountList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = (JsonObject) jsonArray.get(i);
            String item_idx = object.get("item_idx").getAsString();
            String item_color = object.get("item_color").getAsString();
            String item_quantity = object.get("item_quantity").getAsString();

            Long itemIdx = Long.parseLong(item_idx);
            int itemOrderCount = Integer.parseInt(item_quantity);

            Item findItem = itemRepository.findByItemIdxAndColorAndRep(itemIdx, item_color, true);

            itemList.add(findItem.getId());
            itemCountList.add(itemOrderCount);
        }

        itemListToOrderDto.setItemList(itemList);
        itemListToOrderDto.setItemCountList(itemCountList);

        return itemListToOrderDto;
    }

    @Override
    public List<Item> MainCarouselItemList() {
        List<Item> mainCarouselList = new ArrayList<>();

        Item firstItem = itemRepository.findByItemIdxAndColorAndRep(94L, "블루", true);
        Item secondItem = itemRepository.findByItemIdxAndColorAndRep(95L, "블랙", true);
        Item thirdItem = itemRepository.findByItemIdxAndColorAndRep(96L, "네이비", true);
        Item fourthItem = itemRepository.findByItemIdxAndColorAndRep(97L, "블랙 M size", true);
        Item fifthItem = itemRepository.findByItemIdxAndColorAndRep(98L, "아이보리", true);

        mainCarouselList.add(firstItem);
        mainCarouselList.add(secondItem);
        mainCarouselList.add(thirdItem);
        mainCarouselList.add(fourthItem);
        mainCarouselList.add(fifthItem);

        return mainCarouselList;
    }

    @Override
    public List<WeeklyBestDto> OuterWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("outer", "jacket", true);
    }

    @Override
    public List<WeeklyBestDto> SleeveTopWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("top", "jacket", true);
    }

    @Override
    public List<WeeklyBestDto> ShirtsWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("shirts", "jacket", true);
    }

    @Override
    public List<WeeklyBestDto> BottomWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("bottom", "jacket", true);
    }

    @Override
    public List<WeeklyBestDto> ShoesWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("shoes", "jacket", true);
    }

    @Override
    public List<WeeklyBestDto> TopKnitWeeklyBestItem() {
        return itemRepository.findWeeklyBestItem("top", "jacket", true);
    }

    @Override
    public List<WeeklyBestDto> NewArrivalItem() {
        List<WeeklyBestDto> newArrivalList = itemRepository.findNewArrivalItem("outer", "jacket", true);
        for (WeeklyBestDto weeklyBestDto : newArrivalList) {
            weeklyBestDto.setMileage(weeklyBestDto.getPrice() / 100);
        }

        return newArrivalList;
    }
    @Override
    public Page<ItemDto> findAllItem(Pageable pageable) {
        return itemRepository.searchAllItem(pageable);
    }

    @Override
    public Page<ItemDto> findAllItemByCondition(SearchItem searchItem, Pageable pageable) {
        return itemRepository.searchAllItemByCondition(searchItem, pageable);
    }

    @Override
    public Long MaxItemIdx() {
        return itemRepository.searchMaxItemIdx();
    }
}
