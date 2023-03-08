package com.example.shop.repository;

import com.example.shop.model.CustomServiceReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CSReplyRepository extends JpaRepository<CustomServiceReply, Integer> {
}
