package com.example.shop.controller;

import com.example.shop.dto.*;
import com.example.shop.model.*;
import com.example.shop.model.type.OrderStatus;
import com.example.shop.service.ItemServiceImpl;
import com.example.shop.service.OrderServiceImpl;
import com.example.shop.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceImpl userServiceImpl;
    private final ItemServiceImpl itemServiceImpl;
    private final OrderServiceImpl orderServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping("/admin/changepw")
    public String adminChangePassword() {
        return "admin/admin_changePassword";
    }

    @PutMapping("/admin/changepassword_ok")
    public @ResponseBody
    String changeAdminPasswordPage(Principal principal, @RequestParam("password") String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User findUser = userServiceImpl.findUserByLoginId(principal.getName());

        findUser.setPassword(newPassword);
        userServiceImpl.changePw(findUser.getId(), passwordEncoder.encode(newPassword));

        return "Admin P/W Change OK";
    }

    @GetMapping("/admin/main")
    public String getUserMainPage(Model model, @PageableDefault(size = 4) Pageable pageable) {
        Page<User> userBoards = userServiceImpl.findUserAllCreatedAt(pageable);
        Page<ItemDto> itemBoards = itemServiceImpl.findAllItem(pageable);
        Page<OrderDto> orderBoards = orderServiceImpl.findAllOrder(pageable);
        int allVisitCount = userServiceImpl.getVisitCount();

        model.addAttribute("userList", userBoards);
        model.addAttribute("itemList", itemBoards);
        model.addAttribute("orderList", orderBoards);
        model.addAttribute("numVisitors", allVisitCount);

        return "admin/admin_main";
    }

    @GetMapping("/admin/register")
    public String getRegisterItemPage() {
        return "admin/admin_registerGoods";
    }

    @PostMapping("/admin/register")
    public String requestupload2(MultipartHttpServletRequest mtfRequest
            , @RequestParam("cmode1") String firstCategory
            , @RequestParam("cmode2") String secondCategory
            , @RequestParam("cmode3") String thirdCategory
            , @RequestParam("item_name") String itemName
            , @RequestParam("price") int itemPrice
            , @RequestParam("salestatus") String saleStatus
            , @RequestParam("info") String itemInfo
            , @RequestParam("color") String itemColor
            , @RequestParam("size") String itemSize
            , @RequestParam("stock_quantity") int itemQuantity
            , @RequestParam("fabric") String itemFabric
            , @RequestParam("model") String itemModel
    ) {

        String folderPath = "C:\\Users\\LeeHoYoung\\Desktop\\shop\\shop\\src\\main\\resources\\static\\image" + firstCategory + "\\" + secondCategory + "\\" + itemName + "\\";

        File newFile = new File(folderPath);
        if (newFile.mkdirs()) {
            logger.info("directory make ok");
        } else {
            logger.warn("directory can't make");
        }

        List<MultipartFile> fileList = mtfRequest.getFiles("upload_image");
        Long newItemIdx = itemServiceImpl.MaxItemIdx();

        for (int i = 0; i < fileList.size(); i++) {
            String originFileName = fileList.get(i).getOriginalFilename();
            String safeFile = folderPath + originFileName;


            String upperFirstCategory = firstCategory.toUpperCase(Locale.ROOT);
            String newUrl = "/image/Item/" + upperFirstCategory + "/" +  secondCategory  + "/" + itemName + "/" + originFileName;

            Item item = new Item(firstCategory, secondCategory, thirdCategory, itemName, itemPrice, itemInfo, itemColor, itemFabric, itemModel, itemSize, itemQuantity, newUrl, saleStatus, newItemIdx + 1, true);

            item.setRep(i == 0);
            itemServiceImpl.saveItem(item);
            try {
                fileList.get(i).transferTo(new File(safeFile));
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/admin/itemList";
    }

    @GetMapping("/admin/itemList")
    public String itemListPage(Model model, @PageableDefault(size = 5) Pageable pageable, SearchItem searchItem) {
        new ItemPageDto();
        ItemPageDto itemPageDto;
        if (searchItem.getItem_name() == null) {
            itemPageDto = itemServiceImpl.findAllItemByPaging(pageable);
        } else {
            itemPageDto = itemServiceImpl.findAllItemByConditionByPaging(searchItem, pageable);
        }
        Page<ItemDto> itemBoards = itemPageDto.getItemPage();
        int homeStartPage = itemPageDto.getHomeStartPage();
        int homeEndPage = itemPageDto.getHomeEndPage();

        model.addAttribute("productList", itemBoards);
        model.addAttribute("startPage", homeStartPage);
        model.addAttribute("endPage", homeEndPage);

        model.addAttribute("saleStatus", searchItem.getSalestatus());
        model.addAttribute("firstCategory", searchItem.getCmode());
        model.addAttribute("itemName", searchItem.getItem_name());

        return "admin/admin_Goodslist";
    }

    @ResponseBody
    @PatchMapping("/admin/itemList/onsale")
    public String itemStatusOnSalePage(@RequestBody List<Map<String, String>> allData) {
        for (Map<String, String> temp : allData) {
            itemServiceImpl.ItemSale(temp.get("itemIdx"), temp.get("itemColor"));
        }
        return "상품 상태 판매로 변경";
    }

    @ResponseBody
    @PatchMapping("/admin/itemList/soldout")
    public String itemStatusSoldOutPage(@RequestBody List<Map<String, String>> allData) {
        for (Map<String, String> temp : allData) {
            itemServiceImpl.ItemSoldOut(temp.get("itemIdx"), temp.get("itemColor"));
        }
        return "상품 상태 품절로 변경";
    }

    @ResponseBody
    @DeleteMapping("/admin/itemList/remove")
    public String itemdeletePage(@RequestBody List<Map<String, String>> allData) {
        for (Map<String, String> temp : allData) {
            itemServiceImpl.deleteItemById(temp.get("itemIdx"), temp.get("itemColor"));
        }
        return "상품 삭제 완료";
    }

    @GetMapping("/admin/userList")
    public String pageList(Model model, @PageableDefault(size = 4) Pageable pageable, SearchUser searchUser) {
        new UserPageDto();
        UserPageDto userPageDto;

        if (searchUser.getSearchKeyword() == null) {
            userPageDto = userServiceImpl.findAllUserByPaging(pageable);
        } else {
            userPageDto = userServiceImpl.findAllUserByConditionByPaging(searchUser, pageable);
        }

        int homeStartPage = userPageDto.getStartPage();
        int homeEndPage = userPageDto.getEndPage();
        Page<UserDto> userBoards = userPageDto.getUserPage();

        model.addAttribute("startPage", homeStartPage);
        model.addAttribute("endPage", homeEndPage);
        model.addAttribute("userList", userBoards);
        model.addAttribute("searchCondition", searchUser.getSearchCondition());
        model.addAttribute("searchKeyword", searchUser.getSearchKeyword());

        return "admin/admin_userlist";
    }

    @GetMapping("/admin/userList/user/{id}")
    public String pageUser(@PathVariable Long id, Model model) {
        model.addAttribute("User", userServiceImpl.findUserById(id));

        return "admin/admin_user";
    }

    @ResponseBody
    @DeleteMapping("/admin/userList/{id}")
    public String deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteId(id);

        return "회원 삭제 완료";
    }

    @ResponseBody
    @DeleteMapping("/admin/userList")
    public String deleteChecked(@RequestParam(value = "idList", required = false) List<Long> idList) {
        int size = idList.size();

        for (Long aLong : idList) {
            userServiceImpl.deleteId(aLong);
        }
        return "선택된 회원 삭제 완료";
    }

    @GetMapping("/admin/orderList")
    public String getOrderPage(Model model, @PageableDefault(size = 4) Pageable pageable, SearchOrder searchOrder) {

        OrderPageDto orderPageDto = new OrderPageDto();

        if (StringUtils.isEmpty(searchOrder.getFirstdate()) && StringUtils.isEmpty(searchOrder.getLastdate()) && StringUtils.isEmpty(searchOrder.getSinput())) {
            orderPageDto = orderServiceImpl.findAllOrderByPaging(pageable);
        } else {
            orderPageDto = orderServiceImpl.findAllOrderByConditionByPaging(searchOrder, pageable);
        }

        Page<OrderDto> orderBoards = orderPageDto.getOrderBoards();
        int homeStartPage = orderPageDto.getHomeStartPage();
        int homeEndPage = orderPageDto.getHomeEndPage();

        model.addAttribute("orderList", orderBoards);
        model.addAttribute("startPage", homeStartPage);
        model.addAttribute("endPage", homeEndPage);

        model.addAttribute("firstDate", searchOrder.getFirstdate());
        model.addAttribute("lastDate", searchOrder.getLastdate());
        model.addAttribute("oMode", searchOrder.getOmode());
        model.addAttribute("sMode", "buyer");
        model.addAttribute("sInput", searchOrder.getSinput());
        model.addAttribute("oModeStatus", searchOrder.getOmode());

        return "admin/admin_order";

    }

    @ResponseBody
    @PatchMapping("/admin/orderList1/{id}")
    public String orderStatusChangePage(@PathVariable Long id, @RequestParam OrderStatus status) {
        orderServiceImpl.changeOrderStatus(id, status);

        return "주문 상품 상태 변경";
    }
}
