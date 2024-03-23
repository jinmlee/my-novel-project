package com.jinmlee.novel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum MyRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleName;
}