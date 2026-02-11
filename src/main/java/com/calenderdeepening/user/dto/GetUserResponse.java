package com.calenderdeepening.user.dto;

import lombok.Getter;

@Getter
public class GetUserResponse {

    private final Long id;
    private final String author;
    private final String email;

    public GetUserResponse(Long id, String author, String email) {
        this.id = id;
        this.author = author;
        this.email = email;
    }
}
