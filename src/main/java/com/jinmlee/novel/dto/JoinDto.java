package com.jinmlee.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
    private String userId;
    private String userName;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
}
