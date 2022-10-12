package com.automobilepartnership.api.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotNull(message = "나이를 입력해주세요")
    private Integer age;

    @NotBlank(message = "사는 곳을 입력해주세요")
    private String residence;
}