package com.calenderdeepening.comment.dto;

import lombok.Getter;

@Getter
public class UpdateCommentResponse {
    private final Long id;

    public UpdateCommentResponse(Long id) {
        this.id = id;
    }
}