package com.jinmlee.novel.dto.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
    @NotBlank(message = "id는 공백이 있을 수 없습니다.")
    @Size(min = 5, max = 20, message = "id는 5에서 20글자 사이여야 합니다.")
    private String userId;
    @NotBlank(message = "이름에는 공백이 있을 수 없습니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상, 20자 이하로 입력해주세요.")
    private String userName;
    @NotEmpty(message = "닉네임은 필수 입력사항입니다.")
    @Size(max = 20, message = "닉네임은 최대 20자만 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문,숫자,기호를 조합하여 8자 이상, 20자 이하로 입력해주세요.")
    private String password;
    @NotBlank(message = "email은 필수 입력사항입니다.")
    private String email;
    @NotBlank(message = "핸드폰 번호는 필수 입력사항입니다.")
    private String phoneNumber;
}
