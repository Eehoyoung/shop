package com.example.shop.repository;

import com.example.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByloginId(String loginId);

    Page<User> findAllByOrderByCreatedAt(Pageable pageable);

    @Query("select sum(m.visitCount) from User m")
    int visitCountResult();

    void deleteByLoginId(String loginId);
}
