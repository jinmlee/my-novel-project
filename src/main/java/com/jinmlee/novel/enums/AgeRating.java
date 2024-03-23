package com.jinmlee.novel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum AgeRating {
    ALL(0, "전체이용가"),
    ADULT(19, "청소년관람불과");

    private final int age;
    private final String rating;
}
