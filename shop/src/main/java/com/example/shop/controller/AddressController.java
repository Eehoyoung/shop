package com.example.shop.controller;

import com.example.shop.dto.AddressChangeDto;
import com.example.shop.dto.AddressDto;
import com.example.shop.model.DeliveryAddress;
import com.example.shop.service.DeliveryAddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AddressController {

    private final DeliveryAddressServiceImpl deliveryAddressServiceImpl;

    @GetMapping("/main/address")
    public String getAddressPage(Principal principal, Model model) {
        String loginId = principal.getName();
        List<DeliveryAddress> deliveryAddressList = deliveryAddressServiceImpl.DeliveryAddressByLoginId(loginId);

        model.addAttribute("addressList", deliveryAddressList);

        return "main/address";
    }

    @GetMapping("/main/address/register")
    public String getRegisterPage() {
        return "main/register_address";
    }

    @PostMapping("/main/address/register_ok")
    public String registerAddressPage(Principal principal, @ModelAttribute AddressDto addressDto) {
        String loginId = principal.getName();

        deliveryAddressServiceImpl.UserAddres(loginId, addressDto);

        return "redirect:/main/address";
    }

    @ResponseBody
    @DeleteMapping("/main/address/delete")
    public String deleteAddressPage(@RequestParam(value = "addressIdList", required = false) List<Long> addressIdList) {
        for (Long addressId : addressIdList) {
            deliveryAddressServiceImpl.deleteAddressById(addressId);
        }

        return "주소 삭제 완료";
    }

    @GetMapping("/main/address/change/{id}")
    public String getChangeAddressPage(@PathVariable Long id, Model model) {
        AddressChangeDto addressChangeDto = deliveryAddressServiceImpl.addressChange(id);

        model.addAttribute("address", addressChangeDto);

        return "main/change_address";
    }

    @PutMapping("/main/changeaddress_ok")
    public String changeAddressStatus(@ModelAttribute AddressChangeDto addressChangeDto) {

        deliveryAddressServiceImpl.updateDeliveryAddress(addressChangeDto.getId(), addressChangeDto);

        return "redirect:/main/address";
    }
}
