package com.example.shop.repository;

import com.example.shop.model.Mileage;
import com.example.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<Mileage, Long> {
    Page<Mileage> findAllByUser(User user, Pageable pageable);
}
