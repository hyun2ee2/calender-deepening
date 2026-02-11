package com.calenderdeepening.user.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String author;
    private String email;
    private String password;
}
