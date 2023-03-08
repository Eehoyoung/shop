package com.example.shop.repository;

import com.example.shop.model.CustomServiceBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

public interface CSBoardRepository extends JpaRepository<CustomServiceBoard , Integer> {

    @Query(value = "SELECT * FROM customserviceboard WHERE title like %?% and boardType = 'NORMAL' order by id desc", nativeQuery = true)
    Page<CustomServiceBoard> findByTitleContaining(String title, Pageable pageable);

    @Query(value = "SELECT * FROM customserviceboard WHERE boardType = 'NOTICE' order by id desc", nativeQuery = true)
    List<CustomServiceBoard> loadNoticeBoard();
}
