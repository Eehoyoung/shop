package com.example.shop.controller;

import com.example.shop.controller.config.PrincipalDetail;
import com.example.shop.dto.ResponseDto;
import com.example.shop.model.CustomServiceBoard;
import com.example.shop.model.CustomServiceReply;
import com.example.shop.model.type.CSBoardType;
import com.example.shop.model.type.UserGrade;
import com.example.shop.service.CSBoardServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class CSBoardApiController {
    private final CSBoardServiceImpl csBoardServiceImpl;

    public CSBoardApiController(CSBoardServiceImpl csBoardServiceImpl) {
        this.csBoardServiceImpl = csBoardServiceImpl;
    }

    @PostMapping("/cs-write")
    public ResponseDto<Integer> writeBoard(@AuthenticationPrincipal PrincipalDetail principal, @RequestBody CustomServiceBoard serviceBoard) {
        serviceBoard.setUser(principal.getUser());
        if (principal.getUser().getUserGrade() == UserGrade.ADMIN) {
            serviceBoard.setBoardType(CSBoardType.NOTICE);
        } else {
            serviceBoard.setBoardType(CSBoardType.NORMAL);
        }
        csBoardServiceImpl.writeBoard(serviceBoard);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/cs-update")
    public ResponseDto<String> updateBoard(@AuthenticationPrincipal PrincipalDetail principal, @RequestBody CustomServiceBoard board) {
        System.out.println(board);
        CustomServiceBoard tempboard = csBoardServiceImpl.findCSboardByid(board.getId());

        if (!Objects.equals(tempboard.getUser().getId(), principal.getUser().getId())) {
            return new ResponseDto<>(HttpStatus.FORBIDDEN.value(), "NO");
        }

        if (csBoardServiceImpl.updateCsBoard(board)) {
            return new ResponseDto<>(HttpStatus.OK.value(), "OK");
        } else {
            return new ResponseDto<>(HttpStatus.FORBIDDEN.value(), "NO");
        }
    }

    @DeleteMapping("/cs-delete")
    public ResponseDto<String> deleteBaord(@RequestParam(value = "boardId") int id, @AuthenticationPrincipal PrincipalDetail principal) {
        System.out.println("deltet    " + id);
        CustomServiceBoard tempboard = csBoardServiceImpl.findCSboardByid(id);
        if (tempboard.getUser().getId() != principal.getUser().getId()) {
            return new ResponseDto<>(HttpStatus.FORBIDDEN.value(), "NO");
        }
        csBoardServiceImpl.removeBoard(id);
        return new ResponseDto<>(HttpStatus.OK.value(), "OK");
    }

    @PostMapping("/cs-write/reply")
    public ResponseDto<CustomServiceReply> writeReply(@AuthenticationPrincipal PrincipalDetail principal,
                                                      @RequestBody CustomServiceReply customServiceReply,
                                                      @RequestParam(value = "boardId") int id) {
        System.out.println(customServiceReply);
        if (principal.getUser().getUserGrade() != UserGrade.ADMIN) {
            return new ResponseDto<>(HttpStatus.FORBIDDEN.value(), null);
        }

        CustomServiceReply csreply = csBoardServiceImpl.saveReply(customServiceReply, id);
        return new ResponseDto<>(HttpStatus.OK.value(), customServiceReply);
    }

    @DeleteMapping("/cs-delete/reply")
    public ResponseDto<Integer> deleteReply(@AuthenticationPrincipal PrincipalDetail principal, @RequestParam(value = "replyId") int id) {
        if (principal.getUser().getUserGrade() != UserGrade.ADMIN) {
            return new ResponseDto<>(HttpStatus.FORBIDDEN.value(), null);
        }
        csBoardServiceImpl.deleteReply(id);
        return new ResponseDto<>(HttpStatus.OK.value(), id);
    }


}