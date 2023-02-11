package com.example.shop.service;

import com.example.shop.dto.UserInfoDto;
import com.example.shop.dto.UserPageDto;
import com.example.shop.dto.MyPageDto;
import com.example.shop.dto.ProfileDto;
import com.example.shop.model.User;
import com.example.shop.model.SearchUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findUserById(Long id);
    //PK를 이용하여 Member검색

    User findUserByLoginId(String loginId);

    Long joinUser(UserInfoDto userInfoDto);
    //회원가입

    void updateUserInfo(String loginId, ProfileDto profileDto);
    //Member 개인정보 수정

    Long changePw(Long id, String pw);
    //PW 변경

    MyPageDto showUserInfo(String loginId);
    //user 정보 표시

    boolean checkId(String loginId);
    //아이디 중복 확인

    void deleteUser(String loginId);
    //회원 탈퇴

    Long deleteId(Long id); // 회원 삭제

    ProfileDto showUserInfoDetail(String longinId); //개인상세 정보 표시

    Page<User> findUserAll(Pageable pageable);

    UserPageDto findAllUserByPaging(Pageable pageable);

    UserPageDto findAllUserByConditionByPaging(SearchUser searchUser, Pageable pageable);

    int getVisitCount();
//    전체 방문자 수 구하는 기능
}
