package com.calenderdeepening.user.dto;

import lombok.Getter;

@Getter
public class CreateUserResponse {
    private Long id;
    private String author;
    private String email;

    public CreateUserResponse(Long id, String author, String email) {
        this.id = id;
        this.author = author;
        this.email = email;
    }

}
