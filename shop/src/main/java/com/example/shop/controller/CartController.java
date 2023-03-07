package com.example.shop.controller;

import com.example.shop.dto.ItemDto;
import com.example.shop.dto.ItemToCartDto;
import com.example.shop.dto.MyPageDto;
import com.example.shop.model.Cart;
import com.example.shop.model.User;
import com.example.shop.service.CartServiceImpl;
import com.example.shop.service.ItemServiceImpl;
import com.example.shop.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final UserServiceImpl userServiceImpl;
    private final CartServiceImpl cartServiceImpl;
    private final ItemServiceImpl itemServiceImpl;

    @GetMapping("/main/cart")
    public String getCartPage(Principal principal, Model model) {
        String loginId = principal.getName();

        MyPageDto myPageDto = userServiceImpl.showUserInfo(loginId);
        User user = userServiceImpl.findUserByLoginId(loginId);
        List<Cart> cartList = cartServiceImpl.findAllCartByUserId(user.getId());
        List<ItemDto> cartItemDetail = itemServiceImpl.cartItemDetail(cartList);

        model.addAttribute("User", myPageDto);
        model.addAttribute("itemList", cartItemDetail);
        model.addAttribute("cartlist", cartList);

        return "main/cart";
    }
//        회원을 아이디를 통해 찾는다
//        회원 PK를 통해 cart 테이블에서 각 row를 찾는다.
//        찾은 cart PK를 활용해 해당 아이템의 PK를 찾고 itemRepository에서 이 PK를 가지고 아이템 정보를 가져온다.
//        이후 이 정보는 List에 담겨서 보내지는 것이다.

    @ResponseBody
    @PatchMapping("/main/cart/changequantity/{cartId}/{itemQuantity}")
    public String changeQuantityInCart(@PathVariable Long cartId, @PathVariable int itemQuantity) {
        cartServiceImpl.changeCartItemQuantity(cartId, itemQuantity);

        return "수량 변경 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/cart/remove/{cartId}")
    public String deleteItemInCart(@PathVariable Long cartId) {
        cartServiceImpl.deleteCartById(cartId);

        return "장바구니 상품 삭제 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/cart/removeitems")
    public String deleteCartItem(@RequestParam(value = "itemList", required = false) List<Long> itemList) {
        for (Long aLong : itemList) {
            cartServiceImpl.deleteCartById(aLong);
        }

        return "장바구니 상품 삭제 완료";
    }

    @ResponseBody
    @DeleteMapping("/main/cart/removeall")
    public String deleteAllItemInCart(Principal principal) {
        User user = userServiceImpl.findUserByLoginId(principal.getName());
        List<Cart> cartList = cartServiceImpl.findAllCartByUserId(user.getId());

        for (Cart cart : cartList) {
            cartServiceImpl.deleteCartById(cart.getId());
        }

        return "장바구니 비우기 완료";
    }

    @PostMapping("/main/product/cart_ok")
    public String addItemInCartPage(Principal principal, ItemToCartDto itemToCartDto) {
        int quantity = Integer.parseInt(itemToCartDto.getQuantity());
        Long itemIdx = Long.parseLong(itemToCartDto.getItemIdx());
        String color = itemToCartDto.getItemColor();

        itemServiceImpl.putItemInCart(principal.getName(), itemIdx, color, quantity);

        return "redirect:/main/cart";
    }
}
