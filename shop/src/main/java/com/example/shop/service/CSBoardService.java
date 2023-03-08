package com.example.shop.service;

import com.example.shop.model.CustomServiceBoard;
import com.example.shop.model.CustomServiceReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CSBoardService {

    void writeBoard(CustomServiceBoard customServiceBoard);

    Page<CustomServiceBoard> findByTitle(String title, Pageable pageable);

    List<CustomServiceBoard> loadNoticeBoards();

    CustomServiceBoard findCSboardByid(int id);

    Boolean updateCsBoard(CustomServiceBoard board);

    Boolean removeBoard(int id);

    CustomServiceReply saveReply(CustomServiceReply customServiceReply, int boardId);

    void deleteReply(int replyid);

}
