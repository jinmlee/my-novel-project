package com.jinmlee.novel.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MemberModifyDto {

    @NotBlank(message = "이름에는 공백이 있을 수 없습니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상, 20자 이하로 입력해주세요.")
    private String userName;

    @NotEmpty(message = "닉네임은 필수 입력사항입니다.")
    @Size(max = 20, message = "닉네임은 최대 20자만 입력해주세요.")
    private String nickname;

    @NotBlank(message = "핸드폰 번호는 필수 입력사항입니다.")
    private String phoneNumber;

    @NotBlank(message = "email은 필수 입력사항입니다.")
    private String email;

    private String password;
}
