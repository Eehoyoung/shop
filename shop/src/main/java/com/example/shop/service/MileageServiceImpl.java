package com.example.shop.service;

import com.example.shop.dto.MileagePageDto;
import com.example.shop.exception.NotFoundLoginIdException;
import com.example.shop.model.Mileage;
import com.example.shop.model.User;
import com.example.shop.repository.MileageRepository;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MileageServiceImpl implements MileageService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    MileageRepository mileageRepository;


    @Override
    public int totalMileage(String loginId) {
        User user = userRepository.findByloginId(loginId).get();

        int totalMileage = 0;

        for(int i=0; i<user.getMileageList().size(); i++){
            totalMileage += user.getMileageList().get(i).getMileagePrice();
            System.out.println(totalMileage);
        }
        return totalMileage;
    }

    @Override
    public int usedMileage(String loginId) {
        User user = userRepository.findByloginId(loginId).get();

        int usedMileage = 0;

        for(int i=0; i<user.getOrderList().size(); i++){
            usedMileage += user.getOrderList().get(i).getUsedMileagePrice();
        }

        return usedMileage;
    }

    @Override
    public int availableMileage(int totalMileage, int usedMileage) {
        return totalMileage-usedMileage;
    }

    @Override
    public List<Mileage> MileageDetail(String loginId) {
        User user = userRepository.findByloginId(loginId).get();
        System.out.println("check : " + user.getMileageList().size());
        return user.getMileageList();
    }

    @Override
    public Long joinMileage(Long id) {
        User user = userRepository.findById(id).get();
        Mileage mileage = new Mileage();
        mileage.setMileagePrice(2000);
        mileage.setMileageContent("회원 가입을 축하합니다.");
        mileage.setUser(user);

        mileageRepository.save(mileage);

        return mileage.getId();
    }

    @Override
    public MileagePageDto mileagePaging(String loginId, Pageable pageable) {
        MileagePageDto mileagePageDto = new MileagePageDto();

        User findUser = userRepository.findByloginId(loginId).orElseThrow(
                () -> new NotFoundLoginIdException("해당하는 회원이 존재하지 않습니다")
        );
        Page<Mileage> mileageBoards = mileageRepository.findAllByUser(findUser,pageable);
        int homeStartPage = Math.max(1, mileageBoards.getPageable().getPageNumber() - 4);
        int homeEndPage = Math.min(mileageBoards.getTotalPages(), mileageBoards.getPageable().getPageNumber() + 4);

        mileagePageDto.setMileageBoards(mileageBoards);
        mileagePageDto.setHomeStartPage(homeStartPage);
        mileagePageDto.setHomeEndPage(homeEndPage);

        return mileagePageDto;
    }
}
