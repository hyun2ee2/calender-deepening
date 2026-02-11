package com.calenderdeepening.calender.dto;

import lombok.Getter;

@Getter
public class UpdateCalenderResponse {

    private final Long id;
    private final String title;
    private final String content;

    public UpdateCalenderResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
