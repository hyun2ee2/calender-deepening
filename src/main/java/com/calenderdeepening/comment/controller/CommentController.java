package com.calenderdeepening.comment.controller;

import com.calenderdeepening.comment.dto.*;
import com.calenderdeepening.comment.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 생성
    @PostMapping("/calenders/{calenderId}/comments")
    public ResponseEntity<CreateCommentResponse> saveComment(
            @PathVariable Long calenderId,
            @RequestBody CreateCommentRequest request,
            HttpSession session
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(calenderId, request, session));
    }

    // 다 건 조회
    @GetMapping("/calenders/{calenderId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable Long calenderId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(calenderId));
    }

    // 단 건 조회
    @GetMapping("/calenders/{calenderId}/comments/{commentId}")
    public ResponseEntity<GetCommentResponse> getComment(
            @PathVariable Long commentId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getOne(commentId));
    }

    // 수정
    @PutMapping("/calenders/{calenderId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            HttpSession session
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, request, session));
    }

    @DeleteMapping("/calenders/{calenderId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        commentService.delete(commentId, session);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
