package com.jinmlee.novel.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ModifyPwdDto {
    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문,숫자,기호를 조합하여 8자 이상, 20자 이하로 입력해주세요.")
    private String password;

    private String oldPassword;
}
