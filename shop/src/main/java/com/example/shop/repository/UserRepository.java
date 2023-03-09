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

    @Query(value = "select m.UserBuilder from User m where m.name = :name and m.phoneNumber = :phoneNumber",nativeQuery = true)
    User userIdSearch(User search);

    @Query(value = "select count(m.UserBuilder) from User m where m.name = :name and m.phoneNumber = :phoneNumber and m.loginId = :loginId", nativeQuery = true)
    int userPwCheck(User search);

    @Query(value = "update User m set m.password = :pw where m.name = :name and m.phoneNumber = :phoneNumber and m.loginId = :loginId" ,nativeQuery = true)
    void pwUpdate(User search);

    Optional<User> findByLoginId(String username);
}
