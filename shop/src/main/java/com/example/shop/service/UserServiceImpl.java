package com.example.shop.service;

import com.example.shop.dto.*;
import com.example.shop.exception.NotFoundLoginIdException;
import com.example.shop.exception.unSaveIdException;
import com.example.shop.model.User;
import com.example.shop.model.UserAddress;
import com.example.shop.model.SearchUser;
import com.example.shop.model.type.UserGrade;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new unSaveIdException("존재 하지않는 회원 입니다."));
    }

    @Override
    public User findUserByLoginId(String loginId) {
        return userRepository.findByloginId(loginId).orElseThrow(
                () -> new unSaveIdException("존재 하지않는 회원 입니다."));
    }

    @Transactional
    @Override
    public Long joinUser(UserInfoDto userInfoDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

        return userRepository.save(userInfoDto.toEntity()).getId();
    }

    @Transactional
    @Override
    public void updateUserInfo(String loginId, ProfileDto profileDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User findUser = userRepository.findByloginId(loginId).orElseThrow(
                () -> new unSaveIdException("해당하는 회원을 찾을 수 없습니다")
        );

        String homePhoneNumberResult = profileDto.getHomePhoneNumber()[0] + "," + profileDto.getHomePhoneNumber()[1] + "," + profileDto.getHomePhoneNumber()[2];
        String phoneNumberResult = profileDto.getPhoneNumber()[0] + "," + profileDto.getPhoneNumber()[1] + "," + profileDto.getPhoneNumber()[2];
        UserAddress userAddress = new UserAddress(profileDto.getCity(), profileDto.getStreet(), profileDto.getZipcode());

        findUser.setName(profileDto.getName());
        findUser.setLoginId(profileDto.getLoginId());
        findUser.setPassword(passwordEncoder.encode(profileDto.getPassword()));

        findUser.setHomePhoneNumber(homePhoneNumberResult);
        findUser.setPhoneNumber(phoneNumberResult);
        findUser.setEmail(profileDto.getEmail());
        findUser.setUserAddress(userAddress);
    }

    @Transactional
    @Override
    public Long changePw(Long id, String pw){
        User user = findUserById(id);
        user.setPassword(pw);
        return user.getId();
    }


    @Override
    public MyPageDto showUserInfo(String loginId) {
        int totalMileage = 0;
        int usedMileage = 0;
        MyPageDto myPageDto = new MyPageDto();

        User findUser = userRepository.findByloginId(loginId).orElseThrow(
                () -> new NotFoundLoginIdException("해당하는 회원이 존재하지 않습니다")
        );

        for (int i = 0; i < findUser.getMileageList().size(); i++) {
            totalMileage += findUser.getMileageList().get(i).getMileagePrice();
        }
        for (int j = 0; j < findUser.getOrderList().size(); j++) {
            usedMileage += findUser.getOrderList().get(j).getUsedMileagePrice();
        }

        myPageDto.setName(findUser.getName());
        myPageDto.setGrade(findUser.getUserGrade());
        myPageDto.setMileage(totalMileage - usedMileage);

        return myPageDto;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkId(String loginId) {
        Optional<User> findUser = userRepository.findByloginId(loginId);
        return findUser.isPresent();
    }

    @Transactional
    @Override
    public void deleteUser(String loginId) {
        userRepository.deleteByLoginId(loginId);
    }

    @Transactional
    @Override
    public Long deleteId(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    //계정 상세 정보
    @Override
    public ProfileDto showUserInfoDetail(String loginId) {
        ProfileDto profileDto =new ProfileDto();
        User findUser = userRepository.findByloginId(loginId).orElseThrow(
                () -> new NotFoundLoginIdException("해당 회원을 찾을 수 없습니다.")
        );
        String homePhoneNumber = findUser.getHomePhoneNumber();
        String[] homePhoneNumberArr = homePhoneNumber.split(",");
        String phoneNumber = findUser.getPhoneNumber();
        String[] phoneNumberArr = phoneNumber.split(",");
        String birthday = findUser.getBirthday();
        String[] birthdayArr = birthday.split(",");

        if (findUser.getUserAddress() == null) {
            findUser.setUserAddress(new UserAddress("", "", ""));
        }

        profileDto.setName(findUser.getName());
        profileDto.setLoginId(findUser.getLoginId());
        profileDto.setStreet(findUser.getUserAddress().getStreet());
        profileDto.setZipcode(findUser.getUserAddress().getZipcode());
        profileDto.setCity(findUser.getUserAddress().getCity());
        profileDto.setHomePhoneNumber(homePhoneNumberArr);
        profileDto.setPhoneNumber(phoneNumberArr);
        profileDto.setEmail(findUser.getEmail());
        profileDto.setBirthday(birthdayArr);

        return profileDto;
    }

    @Override
    public Page<User> findUserAll(Pageable pageable) {
        return userRepository.findAllByOrderByCreatedAt(pageable);
    }

    @Override
    public UserPageDto findAllUserByPaging(Pageable pageable) {
        UserPageDto userPageDto = new UserPageDto();
        Page<UserDto> userPage = userRepository.searchAll(pageable);
        int homeStartPage = Math.max(1, userPage.getPageable().getPageNumber());
        int homeEndPage = Math.min(userPage.getTotalPages(), userPage.getPageable().getPageNumber() + 5);

        userPageDto.setUserPage(userPage);
        userPageDto.setStartPage(homeStartPage);
        userPageDto.setEndPage(homeEndPage);

        return userPageDto;
    }

    @Override
    public UserPageDto findAllUserByConditionByPaging(SearchUser searchUser, Pageable pageable) {
        UserPageDto userPageDto = new UserPageDto();
        Page<UserDto> userPage = userRepository.searchByCondition(searchUser, pageable);

        int startPage = Math.max(1, userPage.getPageable().getPageNumber() - 2);
        int endPage = Math.min(userPage.getTotalPages(), startPage + 4);

        userPageDto.setUserPage(userPage);
        userPageDto.setStartPage(startPage);
        userPageDto.setEndPage(endPage);

        return userPageDto;
    }

    @Override
    public int getVisitCount() {
        return userRepository.visitCountResult();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntityWrapper = userRepository.findByloginId(username);

        User userEntity = userEntityWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin@example.com").equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserGrade.ADMIN.getValue()));
            userEntity.setUserGrade(UserGrade.ADMIN);
        } else {
            authorities.add(new SimpleGrantedAuthority(UserGrade.USER.getValue()));
            userEntity.setUserGrade(UserGrade.USER);
        }

        int visitCount = userEntity.getVisitCount();
        userEntity.setVisitCount(++visitCount);

        return new org.springframework.security.core.userdetails.User(userEntity.getLoginId(), userEntity.getPassword(), authorities);
    }
}
