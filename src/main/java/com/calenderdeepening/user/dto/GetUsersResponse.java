package com.calenderdeepening.user.dto;

import lombok.Getter;

@Getter
public class GetUsersResponse {

    private final Long id;
    private final String author;


    public GetUsersResponse(Long id, String author) {
        this.id = id;
        this.author = author;
    }
}
