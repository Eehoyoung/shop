package com.example.shop.controller;

import com.example.shop.dto.MyPageDto;
import com.example.shop.dto.MyPageOrderStatusDto;
import com.example.shop.service.OrderItemService;
import com.example.shop.service.OrderService;
import com.example.shop.service.OrderServiceImpl;
import com.example.shop.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class MainController {

    @Autowired
    UserServiceImpl userServiceImpl;


    private final OrderServiceImpl orderServiceImpl;



    @GetMapping("main/mypage")
    public String getMyPage(Principal principal, Model model) {
        String loginId = principal.getName();

        MyPageDto myPageDto = userServiceImpl.showUserInfo(loginId);
        MyPageOrderStatusDto myPageOrderStatusDto = orderServiceImpl.showOrderStatus(loginId);

        model.addAttribute("user", myPageDto);
        model.addAttribute("orderStatus", myPageOrderStatusDto);

        return "main/mypage";
    }
}
