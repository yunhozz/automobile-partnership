package com.automobilepartnership.api.dto.counsel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CounselRequestDto {

    @NotBlank(message = "상담 종류를 입력해주세요")
    private String type;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "상담 내용을 입력해주세요")
    private String detail;
}