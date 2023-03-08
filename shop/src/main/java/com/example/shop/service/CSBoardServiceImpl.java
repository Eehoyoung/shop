package com.example.shop.service;

import com.example.shop.model.CustomServiceBoard;
import com.example.shop.model.CustomServiceReply;
import com.example.shop.repository.CSBoardRepository;
import com.example.shop.repository.CSReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CSBoardServiceImpl implements CSBoardService {
    private final CSBoardRepository csBoardRepository;

    private final CSReplyRepository csReplyRepository;

    @Transactional
    @Override
    public void writeBoard(CustomServiceBoard customServiceBoard) {
        csBoardRepository.save(customServiceBoard);
    }

    @Transactional
    @Override
    public Page<CustomServiceBoard> findByTitle(String title, Pageable pageable) {
        return csBoardRepository.findByTitleContaining(title, pageable);
    }

    @Transactional
    @Override
    public List<CustomServiceBoard> loadNoticeBoards() {
        return csBoardRepository.loadNoticeBoard();
    }

    @Transactional
    @Override
    public CustomServiceBoard findCSboardByid(int id) {
        return csBoardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시글을 찾을수 없습니다.")
        );
    }

    @Transactional
    @Override
    public Boolean updateCsBoard(CustomServiceBoard board) {
        CustomServiceBoard tempBoard = csBoardRepository.findById(board.getId()).orElseThrow(() -> new RuntimeException("해당 게시글를 찾을 수 없습니다."));
        tempBoard.setTitle(board.getTitle());
        tempBoard.setContent(board.getContent());
        tempBoard.setSecret(board.getSecret());
        return true;
    }

    @Transactional
    @Override
    public Boolean removeBoard(int id) {
        csBoardRepository.deleteById(id);
        return true;
    }

    @Transactional
    @Override
    public CustomServiceReply saveReply(CustomServiceReply customServiceReply, int boardId) {
        CustomServiceBoard targetBoard = csBoardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("해당 게시글를 찾을 수 없습니다."));
        customServiceReply.setCustomServiceBoard(targetBoard);
        csReplyRepository.save(customServiceReply);
        return customServiceReply;
    }

    @Transactional
    @Override
    public void deleteReply(int replyid) {
        csReplyRepository.deleteById(replyid);
    }
}
