package com.example.shop.controller;

import com.example.shop.dto.UserInfoDto;
import com.example.shop.service.MileageServiceImpl;
import com.example.shop.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class UserContoller {

    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    MileageServiceImpl mileageServiceImpl;

    @ApiOperation("로그인")
    @GetMapping("/main/login")
    public String login(HttpServletRequest req, @RequestParam(value = "error", required = false) String error, Model model) {

        String session = req.getHeader("Referer");
        if(session != null){
            req.getSession().setAttribute("prevPage" , session);
        }else {
            session = "http://localhost:8080/main";
            req.getSession().setAttribute("prevPage", session);
        }
        model.addAttribute("error", error);
        return  "main/login";
    }

    @ApiOperation("회원가입")
    @GetMapping("/main/join")
    public String getJoin(){
        return "main/join";
    }


    @PostMapping("main/join")
    public String doRegisterPage(UserInfoDto userInfoDto) {
        Long userId = userServiceImpl.joinUser(userInfoDto);
        mileageServiceImpl.joinMileage(userId);

        return "redirect:/main/login";
    }

    @GetMapping("/defaultUrl")
    public String redirectPage(HttpServletRequest req){
        req.getHeader("Referer");
        String session;

        session = "http://localhost:8080/main";
        req.getSession().setAttribute("prevPage", session);

        return "redirect:/main";
    }


}
