package com.calenderdeepening.comment.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    private final String content;

    public UpdateCommentRequest(String content) {
        this.content = content;
    }
}
