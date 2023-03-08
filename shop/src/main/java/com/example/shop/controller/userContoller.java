package com.example.shop.controller;

import com.example.shop.controller.config.SecurityConfig;
import com.example.shop.dto.MyPageDto;
import com.example.shop.dto.MyPageOrderStatusDto;
import com.example.shop.dto.ProfileDto;
import com.example.shop.dto.UserInfoDto;
import com.example.shop.model.User;
import com.example.shop.service.MileageServiceImpl;
import com.example.shop.service.OrderServiceImpl;
import com.example.shop.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class userContoller {
    private final UserServiceImpl userServiceImpl;

    private final MileageServiceImpl mileageServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    private final SecurityConfig securityConfig;

    @ApiOperation("로그인")
    @GetMapping("/main/login")
    public String login(HttpServletRequest req, @RequestParam(value = "error", required = false) String error, Model model) {

        String session = req.getHeader("Referer");
        if (session != null) {
            req.getSession().setAttribute("prevPage", session);
        } else {
            session = "http://localhost:9090/main/index";
            req.getSession().setAttribute("prevPage", session);
        }
        model.addAttribute("error", error);
        return "main/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/main/index";
    }

    @GetMapping("main/join")
    public String join() {
        return "main/join";
    }

    @PostMapping("main/join")
    public String join(UserInfoDto userInfoDto) {
        Long userId = userServiceImpl.joinUser(userInfoDto);
        mileageServiceImpl.joinMileage(userId);

        return "redirect:/main/login";
    }

    @GetMapping("/defaultUrl")
    public String defaultUrl(HttpServletRequest req) {
        req.getHeader("Referer");
        String session;

        session = "http://localhost:9090/main/index";
        req.getSession().setAttribute("prevPage", session);

        return "redirect:/main/index";
    }

    @GetMapping("/main/mypage")
    public String getMyPage(Principal principal, Model model) {
        String loginId = principal.getName();

        MyPageDto myPageDto = userServiceImpl.showUserInfo(loginId);
        MyPageOrderStatusDto myPageOrderStatusDto = orderServiceImpl.showOrderStatus(loginId);

        model.addAttribute("user", myPageDto);
        model.addAttribute("orderStatus", myPageOrderStatusDto);

        return "main/mypage";
    }

    @PutMapping("/update")
    public String editDataPage(Principal principal, @ModelAttribute("user") ProfileDto profileDto) {

        userServiceImpl.updateUserInfo(principal.getName(), profileDto);

        return "redirect:/main/mypage";
    }

    @GetMapping("/main/profile")
    public String editDataPage(Principal principal, Model model, @ModelAttribute("user") User user) {
        String loginId = principal.getName();
        ProfileDto myProfileDto = userServiceImpl.showUserInfoDetail(loginId);

        model.addAttribute("User", myProfileDto);

        return "main/editdata";
    }

    @ResponseBody
    @DeleteMapping("/main/withdrawal")
    public String withdrawal(HttpServletRequest req, Principal principal, @RequestParam(value = "user_pw") String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String loginId = principal.getName();
        User findUser = userServiceImpl.findUserByLoginId(loginId);

        boolean result = passwordEncoder.matches(password, findUser.getPassword());

        if (result) {
            userServiceImpl.deleteUser(loginId);
            HttpSession s = req.getSession();
            s.invalidate();
            return "정상적으로 회원탈퇴되었습니다.";
        } else {
            return "비밀번호가 올바르지 않습니다";
        }
    }

    @ResponseBody
    @PostMapping("/main/join/doublecheck")
    public String idDoubleCheckPage(@RequestParam(value = "userID") String userId) {
        if (userServiceImpl.checkId(userId)) {
            return "사용할 수 없는 아이디입니다.";
        } else {
            return "사용할 수 있는 아이디입니다.";
        }
    }

    @GetMapping("/account/search_id")
    public String SearchId(HttpServletRequest request, Model model, User search) {
        return "main/search_id";
    }

    @GetMapping("/account/search_pw")
    public String SearchPw(HttpServletRequest request, Model model, User search) {
        return "main/search_pw";
    }

    @PostMapping(value = "/account/search_result_id")
    public String SearchIdResult(HttpServletRequest request, Model model,
                                 @RequestParam(required = true, value = "name") String name,
                                 @RequestParam(required = true, value = "phoneNumber") String phoneNumber, User search) {
        try {
            search.setName(name);
            search.setPhoneNumber(phoneNumber);
            User userSearch = userServiceImpl.userIdSearch(search);

            model.addAttribute("search", userSearch);


        } catch (Exception e) {
            System.out.println(e.toString());
            model.addAttribute("msg", "오류 발생");
        }
        return "main/search_result_id";
    }

    @PostMapping("/account/search_result_pw")
    public String SearchPwResult(HttpServletRequest request, Model model,
                                 @RequestParam(required = true, value = "name") String name,
                                 @RequestParam(required = true, value = "phoneNumber") String phoneNumber,
                                 @RequestParam(required = true, value = "loginId") String loginId, User search) {
        try {
            search.setName(name);
            search.setPhoneNumber(phoneNumber);
            search.setLoginId(loginId);
            int userSearch = userServiceImpl.userPwSearch(search);
            if (userSearch == 0) {
                model.addAttribute("msg", "입력하신 정보를 다시 한번 확인해주세요.");
                return "redirect:/account/search_pw";
            }

            String newPw = RandomStringUtils.randomAlphanumeric(10);
            String enPw = String.valueOf(User.builder().password(newPw));
            search.setPassword(enPw);
            userServiceImpl.pwUpdate(search);
            model.addAttribute("newPw", newPw);

        } catch (Exception e) {
            System.out.println(e.toString());
            model.addAttribute("msg", "오류가 발생 하였습니다.");
        }
        return "main/search_result_pwd";
    }


}
