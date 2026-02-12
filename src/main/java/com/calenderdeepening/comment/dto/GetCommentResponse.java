package com.calenderdeepening.comment.dto;

import lombok.Getter;

@Getter
public class GetCommentResponse {
    private final Long id;
    private final String content;

    public GetCommentResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}

