package com.automobilepartnership.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @Email(message = "정확한 이메일 형식으로 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotNull(message = "나이를 입력해주세요")
    private Integer age;

    @NotBlank(message = "사는 곳을 입력해주세요")
    private String residence;

    private String imageUrl;
}