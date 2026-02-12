package com.calenderdeepening.comment.dto;

import lombok.Getter;

@Getter
public class CreateCommentResponse {

    private final Long id;
    private final String content;

    public CreateCommentResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}