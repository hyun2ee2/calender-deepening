package com.calenderdeepening.user.service;

import com.calenderdeepening.user.dto.*;
import com.calenderdeepening.user.entity.User;
import com.calenderdeepening.user.repository.UserRepository;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 추가
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(request.getAuthor(), request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getPassword()
        );
    }

    // 다 건 조회
    @Transactional(readOnly = true)
    public List<GetUsersResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetUsersResponse> dtos = new ArrayList<>();
        for(User user : users) {
            GetUsersResponse dto = new GetUsersResponse(
                    user.getId(),
                    user.getAuthor()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // 단 건 조회
    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 사용자입니다.")
        );
        return new GetUserResponse(
                user.getId(),
                user.getAuthor(),
                user.getEmail()
        );
    }

    // 업데이트
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 사용자입니다.")
        );

        // 패스워드 검증
        if(!user.getPassword().equals(request.getPassword())) {
            throw new IllegalStateException("패스워드가 일치하지 않습니다.");
        }
        user.updateProfile(request.getAuthor(), request.getEmail());
        return new UpdateUserResponse(user.getId());
    }

    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 사용자입니다."));

        // 패스워드 검증
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalStateException("패스워드가 일치하지 않습니다.");
        }
        userRepository.delete(user);
    }
}
