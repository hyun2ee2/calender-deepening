package com.calenderdeepening.calender.dto;

import lombok.Getter;

@Getter
public class CreateCalenderResponse {

    private final Long id;
    private final String title;
    private final String content;

    public CreateCalenderResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
