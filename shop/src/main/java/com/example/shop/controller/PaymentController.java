package com.example.shop.controller;

import com.example.shop.dto.*;
import com.example.shop.model.DeliveryAddress;
import com.example.shop.model.User;
import com.example.shop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final CartServiceImpl cartServiceImpl;
    private final ItemServiceImpl itemServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final DeliveryAddressServiceImpl deliveryAddressServiceImpl;
    private final OrderServiceImpl orderServiceImpl;

    @PostMapping("/main/payment")
    public String getPaymentDataPage(Principal principal, Model model, @RequestParam(value = "itemlist") String itemlist, @RequestParam(value = "where") String where) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        if (where.equals("cart")) {
            itemDtoList = cartServiceImpl.CartListToPayment(itemlist);
        } else if (where.equals("product")) {
            itemDtoList = itemServiceImpl.Payment(itemlist);
        }

        String loginId = principal.getName();
        MyPageDto myPageDto = userServiceImpl.showUserInfo(loginId);
        List<DeliveryAddress> deliveryAddressList = deliveryAddressServiceImpl.DeliveryAddressByLoginId(loginId);

        model.addAttribute("orderList", itemDtoList);
        model.addAttribute("addressList", deliveryAddressList);
        model.addAttribute("user", myPageDto);

        return "main/payment";
    }

    @ResponseBody
    @PostMapping("/main/payment/changeaddress/{deliveryAddressId}")
    public AddressChangeDto chnageDeliveryAddressInfo(@PathVariable Long deliveryAddressId) {

        return deliveryAddressServiceImpl.addressChange(deliveryAddressId);
    }

    @PostMapping("main/payment_ok")
    public String doPaymentPage(Principal principal, @RequestParam(value = "orderiteminfo") String orderItemInfo, PaymentAddressDto paymentAddressDto, PaymentPriceDto paymentPriceDto, Model model) {
        User user = userServiceImpl.findUserByLoginId(principal.getName());
        ItemListToOrderDto itemListToOrderDto = itemServiceImpl.Order(orderItemInfo);
        MyPageDto myPageDto = userServiceImpl.showUserInfo(principal.getName());
        paymentPriceDto.setEarnMileage(paymentPriceDto.getTobepaid_price() * 0.01);

        orderServiceImpl.doOrder(user.getId(), itemListToOrderDto.getItemList(), itemListToOrderDto.getItemCountList(), paymentAddressDto, paymentPriceDto);

        model.addAttribute("user", myPageDto);
        model.addAttribute("payment", paymentPriceDto);
        model.addAttribute("address", paymentAddressDto);

        return "main/payment_complete";
    }
}
