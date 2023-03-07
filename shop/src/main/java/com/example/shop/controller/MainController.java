package com.example.shop.controller;

import com.example.shop.dto.*;
import com.example.shop.model.*;
import com.example.shop.model.type.OrderStatus;
import com.example.shop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final OrderItemServiceImpl orderItemServiceImpl;
    private final MileageServiceImpl mileageServiceImpl;
    private final OrderServiceImpl orderServiceImpl;
    private final ItemServiceImpl itemServiceImpl;



    @GetMapping("main/order")
    public String OrderPage(Principal principal, @PageableDefault(size = 5) Pageable pageable, Model model, SearchOrder searchOrder) {
        String loginId = principal.getName();
        OrderMainPageDto orderPagingDto = orderServiceImpl.getOrderPagingDto(searchOrder, pageable, loginId);

        model.addAttribute("startPage", orderPagingDto.getHomeStartPage());
        model.addAttribute("endPage", orderPagingDto.getHomeEndPage());
        model.addAttribute("orderList", orderPagingDto.getMainPageOrderBoards());
        model.addAttribute("oMode", searchOrder.getOmode());
        model.addAttribute("firstDate", searchOrder.getFirstdate());
        model.addAttribute("lastDate", searchOrder.getLastdate());
//        order 페이지 내에서의 검색어

        model.addAttribute("omodeStatus", searchOrder.getOmode());
        model.addAttribute("firstdateStatus", searchOrder.getFirstdate());
        model.addAttribute("lastdateStatus", searchOrder.getLastdate());
//        마이페이지에서 order 페이지로 들어올때의 검색어

        return "main/order";
    }

    @ResponseBody
    @PatchMapping("/main/orderStatusChange/{id}")
    public String mainOrderStatusChangePage(@PathVariable Long id, @RequestParam OrderStatus status) {
        orderItemServiceImpl.chagneOrderStatus(id, status);

        return "주문상태 변경완료";
    }

    @GetMapping("/main/mileage")
    public String getMileagePage(Principal principal, Model model, @PageableDefault(size = 5) Pageable pageable) {
        String loginId = principal.getName();

        int totalMileage = mileageServiceImpl.totalMileage(loginId);
        int totalUsedMileage = mileageServiceImpl.usedMileage(loginId);

        MileagePageDto mileagePagingDto = mileageServiceImpl.mileagePaging(loginId, pageable);

        model.addAttribute("totalmileage", totalMileage);
        model.addAttribute("usedmileage", totalUsedMileage);
        model.addAttribute("restmileage", totalMileage - totalUsedMileage);

        model.addAttribute("startPage", mileagePagingDto.getHomeStartPage());
        model.addAttribute("endPage", mileagePagingDto.getHomeEndPage());

        model.addAttribute("mileageList", mileagePagingDto.getMileageBoards());

        return "main/mileage";
    }

    @GetMapping("/main/category/{firstCategory}/{secondCategory}")
    public String getCategoryPage(@PathVariable String firstCategory, @PathVariable String secondCategory, @PageableDefault(size = 12) Pageable pageable, Model model) {
        ItemPageDto itemPagingDto = itemServiceImpl.serchItemPaging(pageable, firstCategory, secondCategory);

        model.addAttribute("startPage", itemPagingDto.getHomeStartPage());
        model.addAttribute("endPage", itemPagingDto.getHomeEndPage());
        model.addAttribute("itemList", itemPagingDto.getItemPage());
        model.addAttribute("categoryName", secondCategory);
        model.addAttribute("firstCategory", firstCategory);
        model.addAttribute("secondCategory", secondCategory);

        return "main/category";
    }

    @GetMapping("/main/product/{itemIdx}")
    public String productPage(@PathVariable Long itemIdx, Model model) {
        ItemDetailDto itemDetailDto = itemServiceImpl.getItemDetailDto(itemIdx);

        model.addAttribute("item", itemDetailDto);

        return "main/product";
    }



    @GetMapping("/main/index")
    public String getMainPage(Model model) {
        List<Item> mainCarouselList = itemServiceImpl.MainCarouselItemList();

        List<WeeklyBestDto> outerWeeklyBestItem = itemServiceImpl.OuterWeeklyBestItem();
        List<WeeklyBestDto> bottomWeeklyBestItem = itemServiceImpl.BottomWeeklyBestItem();
        List<WeeklyBestDto> shirtsWeeklyBestItem = itemServiceImpl.ShirtsWeeklyBestItem();
        List<WeeklyBestDto> shoesWeeklyBestItem = itemServiceImpl.ShoesWeeklyBestItem();
        List<WeeklyBestDto> sleeveTopWeeklyBestItem = itemServiceImpl.SleeveTopWeeklyBestItem();
        List<WeeklyBestDto> topKnitWeeklyBestItem = itemServiceImpl.TopKnitWeeklyBestItem();
        List<WeeklyBestDto> newArrivalItemList = itemServiceImpl.NewArrivalItem();


        model.addAttribute("mainCarousel", mainCarouselList);
        model.addAttribute("outerList", outerWeeklyBestItem);
        model.addAttribute("topList", sleeveTopWeeklyBestItem);
        model.addAttribute("shirtList", shirtsWeeklyBestItem);
        model.addAttribute("knitList", topKnitWeeklyBestItem);
        model.addAttribute("bottomList", bottomWeeklyBestItem);
        model.addAttribute("shoesList", shoesWeeklyBestItem);
        model.addAttribute("newarrivals", newArrivalItemList);

        return "main/index";
    }

    @GetMapping("/main/restrict")
    public String getRestrictPage() {
        return "main/restrict";
    }

    @GetMapping("/search/list")
    public String searchItem(Model model, @PageableDefault(size = 10) Pageable pageable, SearchItem searchItem){
        new ItemPageDto();
        ItemPageDto itemPageDto;

        if(searchItem.getSearchItemKeyword() == null){
            itemPageDto = itemServiceImpl.findAllItemByPaging(pageable);
        } else {
            itemPageDto = itemServiceImpl.findAllItemByConditionByPaging(searchItem, pageable);
        }

        int homeStartPage = itemPageDto.getHomeStartPage();
        int homeEndPage = itemPageDto.getHomeEndPage();
        Page<ItemDto> itemBoards = itemPageDto.getItemPage();

        model.addAttribute("startPage", homeStartPage);
        model.addAttribute("endPage", homeEndPage);
        model.addAttribute("ItemList", itemBoards);
        model.addAttribute("searchCondition", searchItem.getSearchItemCondition());
        model.addAttribute("searchKeyword", searchItem.getSearchItemKeyword());
        model.addAttribute("Salestatus",searchItem.getSalestatus());

        return "main/itmeList1";

    }

    @GetMapping("/search/list/item/{id}")
    public String pageItem(@PathVariable Long id, Model model){
        model.addAttribute("Item", itemServiceImpl.findItemById(id));

        return "main/itemList2";
    }

}
