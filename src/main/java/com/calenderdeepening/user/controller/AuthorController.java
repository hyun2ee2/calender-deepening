package com.calenderdeepening.user.controller;

import com.calenderdeepening.user.dto.LoginRequest;
import com.calenderdeepening.user.entity.User;
import com.calenderdeepening.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final UserRepository userRepository;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 이메일입니다.")
        );

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalStateException("패스워드가 일치하지 않습니다.");
        }

        // 세션 저장
        session.setAttribute("LOGIN_USER", user.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

}
