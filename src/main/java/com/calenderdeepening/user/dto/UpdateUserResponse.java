package com.calenderdeepening.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserResponse {

    private final Long id;

    public UpdateUserResponse(Long id) {
        this.id = id;
    }
}
