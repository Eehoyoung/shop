package com.example.shop.service;

import com.example.shop.dto.AddressChangeDto;
import com.example.shop.dto.AddressDto;
import com.example.shop.exception.AddressNotFoundException;
import com.example.shop.exception.NotFoundLoginIdException;
import com.example.shop.model.DeliveryAddress;
import com.example.shop.model.User;
import com.example.shop.repository.DeliveryAddressRepository;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service

public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void UserAddres(String loginId, AddressDto addressDto) {
        User findUser = userRepository.findByloginId(loginId).orElseThrow(
                () -> new NotFoundLoginIdException("존재하지 않는 회원입니다.")
        );
        DeliveryAddress deliveryAddress = new DeliveryAddress();

        deliveryAddress.setRecipient(addressDto.getRecipient());
        deliveryAddress.setCity(addressDto.getCity());
        deliveryAddress.setStreet(addressDto.getStreat());
        deliveryAddress.setZipcode(addressDto.getZipcode());
        deliveryAddress.setAddressPhoneNumber(addressDto.getAddressPhoneNumber());
        deliveryAddress.setAddressHomePhoneNumber(addressDto.getAddressHomePhoneNumber());
        deliveryAddress.setPlaceName(addressDto.getPlaceName());
        deliveryAddress.setUser(findUser);

        deliveryAddressRepository.save(deliveryAddress);
    }

    @Override
    public DeliveryAddress findAddressById(Long id) {

        return deliveryAddressRepository.findById(id).orElseThrow(
                () -> new AddressNotFoundException("해당하는 주소가 없습니다")
        );
    }

    @Override
    public AddressChangeDto addressChange(Long id) {
        DeliveryAddress findDeliveryAddress = deliveryAddressRepository.findById(id).orElseThrow(
                () -> new AddressNotFoundException("해당하는 주소가 존재하지 않습니다")
        );
        AddressChangeDto addressChangeDto = new AddressChangeDto();

        String[] addressHomePhoneNumber = findDeliveryAddress.getAddressHomePhoneNumber().split(",");
        String[] addressPhoneNumber = findDeliveryAddress.getAddressPhoneNumber().split(",");

        addressChangeDto.setId(findDeliveryAddress.getId());
        addressChangeDto.setPlaceName(findDeliveryAddress.getPlaceName());
        addressChangeDto.setRecipient(findDeliveryAddress.getRecipient());
        addressChangeDto.setCity(findDeliveryAddress.getCity());
        addressChangeDto.setZipcode(findDeliveryAddress.getZipcode());
        addressChangeDto.setStreat(findDeliveryAddress.getStreet());
        addressChangeDto.setAddressPhoneNumber(addressPhoneNumber);
        addressChangeDto.setAddressHomePhoneNumber(addressHomePhoneNumber);

        return addressChangeDto;
    }


    @Override
    public List<DeliveryAddress> DeliveryAddressByLoginId(String loginId) {
        return deliveryAddressRepository.findAllByUserLoginId(loginId);
    }

    @Override
    public void deleteAddressById(Long id) {
        deliveryAddressRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateDeliveryAddress(Long id, AddressChangeDto addressChangeDto) {
        Optional<DeliveryAddress> findDeliveryAddress = deliveryAddressRepository.findById(id);
        DeliveryAddress deliveryAddress = findDeliveryAddress.orElseThrow(
                () -> new AddressNotFoundException("해당하는 주소가 존재하지 않습니다")
        );

        String addressPhoneNumberResult = addressChangeDto.getAddressPhoneNumber()[0] + "," + addressChangeDto.getAddressPhoneNumber()[1] + "," + addressChangeDto.getAddressPhoneNumber()[2];
        String addressHomePhoneNumberResult = addressChangeDto.getAddressHomePhoneNumber()[0] + "," + addressChangeDto.getAddressHomePhoneNumber()[1] + "," + addressChangeDto.getAddressHomePhoneNumber()[2];

        deliveryAddress.setPlaceName(addressChangeDto.getPlaceName());
        deliveryAddress.setRecipient(addressChangeDto.getRecipient());
        deliveryAddress.setAddressHomePhoneNumber(addressHomePhoneNumberResult);
        deliveryAddress.setAddressPhoneNumber(addressPhoneNumberResult);
        deliveryAddress.setZipcode(addressChangeDto.getZipcode());
        deliveryAddress.setCity(addressChangeDto.getCity());
        deliveryAddress.setStreet(addressChangeDto.getStreat());
    }
}
