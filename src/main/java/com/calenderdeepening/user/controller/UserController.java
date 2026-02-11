package com.calenderdeepening.user.controller;

import com.calenderdeepening.user.dto.*;
import com.calenderdeepening.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    // POST
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
            @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    // 다 건 GET
    @GetMapping
    public ResponseEntity<List<GetUsersResponse>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    // 단 건 GET
    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponse> getUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(userId));
    }

    // PUT
    @PutMapping("/{userId}")
    public ResponseEntity<UpdateUserResponse> UpdateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }

    // DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            @RequestBody DeleteUserRequest request
    ) {
        userService.delete(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
