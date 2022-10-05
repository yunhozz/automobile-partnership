package com.automobilepartnership.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotValidResponseDto {

    private String field;
    private String code;
    private Object rejectValue;
    private String message;
}