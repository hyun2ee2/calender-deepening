package com.calenderdeepening.comment.service;

import com.calenderdeepening.calender.entity.Calender;
import com.calenderdeepening.calender.repository.CalenderRepository;
import com.calenderdeepening.comment.dto.*;
import com.calenderdeepening.comment.entity.Comment;
import com.calenderdeepening.comment.repository.CommentRepository;
import com.calenderdeepening.user.entity.User;
import com.calenderdeepening.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    // 3layout 아키텍처라서 컨트롤러 -> 서비스 -> 리포지토리 순이라서 CommentService에는 CommentRepository가 들어가야함.
    private final CommentRepository commentRepository;
    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;

    // 생성
    @Transactional
    public CreateCommentResponse save(Long calenderId, CreateCommentRequest request, HttpSession session) {
        Long usersId = (Long) session.getAttribute("LOGIN_USER");
        User loginUser = userRepository.findById(usersId).orElseThrow(
                () -> new IllegalStateException("로그인이 필요한 서비스입니다.")
        );

        Calender calender = calenderRepository.findById(calenderId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        Comment comment = new Comment(request.getContent(), loginUser, calender);
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent()
        );
    }

    // 다 건 조회
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(HttpSession session) {
        User loginUser = getLoginUser(session);

        return commentRepository.findByUser_Id(loginUser.getId()).stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                        comment.getContent()
                ))
                .toList();
    }

    // 단 건 조회
    @Transactional(readOnly = true)
    public GetCommentResponse getOne(HttpSession session, Long commentId) {
        // SESSION에서 로그인한 정보를 가져옵니다.
        // 1. SESSION이 존재하는가. (만약에 없다면, 로그인이 필요한 서비스입니다.)
        // 2. SESSION이 존재를 하면 저장소에서 유저를 가져와서 실제로 존재하는지 체크를 합니다. (그리고 USER를 가져옵니다.)
        User loginUser = getLoginUser(session);

        // COMMENT ID로 댓글을 가져옵니다.
        // 1. COMMENT가 존재하지 않으면 없다고 띄웁니다.
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글입니다.")
        );

        // COMMENT에 있는 user <- 작성한 사람. user.getId() 작성한 사람의 아이디와 로그인한 유저의 아이디가 같은지 체크합니다.
        if (!loginUser.getId().equals(comment.getUser().getId())) {
            throw new IllegalStateException("본인의 댓글만 불러올 수 있습니다.");
        }

        return new GetCommentResponse(comment.getId(), comment.getContent());
    }

    // 수정
    @Transactional
    public UpdateCommentResponse update(Long commentId, UpdateCommentRequest request, HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");

        if (loginUserId == null) {
            throw new IllegalStateException("로그인이 필요한 서비스입니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글입니다.")
        );
        if (!comment.getUser().getId().equals(loginUserId)) {
            throw new IllegalStateException("본인의 댓글만 수정이 가능합니다.");
        }
        comment.update(request.getContent());
        return new UpdateCommentResponse(comment.getId());
    }

    // 삭제
    @Transactional
    public void delete(Long commentId, HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");
        if (loginUserId == null) {
            throw new IllegalStateException("로그인이 필요한 서비스입니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글입니다.")
        );
        boolean existence = commentRepository.existsById(commentId);
        if (!comment.getUser().getId().equals(loginUserId)) {
            throw new IllegalStateException("본인의 댓글만 삭제가 가능합니다.");
        }
        commentRepository.deleteById(commentId);
    }

    private User getLoginUser(HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");
        if (loginUserId == null) {
            throw new IllegalStateException("로그인이 필요한 서비스입니다.");
        }

        System.out.println("로그인 유저 아이디: " + loginUserId);

        return userRepository.findById(loginUserId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
    }
}
