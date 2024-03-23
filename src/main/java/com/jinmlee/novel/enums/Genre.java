package com.jinmlee.novel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum Genre {
    FANTASY("판타지소설"),
    MYSTERY("미스터리소설"),
    ROMANCE("로맨스소설"),
    REASONING("추리소설"),
    MODERN("현대소설");

    private final String genreName;
}
