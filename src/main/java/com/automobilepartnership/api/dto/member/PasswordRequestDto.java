package com.automobilepartnership.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequestDto {

    @NotBlank(message = "기존 비밀번호를 입력해주세요")
    private String oldPw;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요")
    private String newPw;
}